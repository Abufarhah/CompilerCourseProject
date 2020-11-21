package edu.birzeit.compiler.controllers;

import edu.birzeit.compiler.models.Row;
import edu.birzeit.compiler.models.RowFactory;
import edu.birzeit.compiler.models.State;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Controller {

    ArrayList<Character> allTransitions;
    ConcurrentHashMap<String, State> table;
    File file;
    int step;

    @FXML
    Button next;

    @FXML
    Button checkButton;

    @FXML
    Label state;

    @FXML
    TextField textField;

    @FXML
    Label resultLabel;

    @FXML
    TableView tableView;


    public void buttonAction() {
        System.out.println("Started");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select NDFSA file");
        fileChooser.setInitialDirectory(new File("."));
        file = fileChooser.showOpenDialog(tableView.getScene().getWindow());
        if (file != null) {
            allTransitions = new ArrayList<>();
            table = readFile(allTransitions, file);
            state.setText("Initial Transition Table");
            populateTable();
        }
    }

    public void next(){
        if(step==0){
            removeLambdas(table);
            state.setText("Removal of Lambda Transitions");
            populateTable();
            ++step;
        }else if(step==1){
            removeNonDeterministic(table);
            state.setText("Removal of Non-Determinism");
            populateTable();
            ++step;
        }else if(step==2){
            removeNonAccessible(table);
            state.setText("Removal of In-Accessible States");
            populateTable();
            ++step;
        }else if(step==3){
            mergeEquivalent(table, allTransitions);
            state.setText("Merging Equivalent States");
            populateTable();
            checkButton.setVisible(true);
            ++step;
        }
    }

    public void checkButtonAction() {
        if (table != null && allTransitions != null) {
            String string = textField.getText();
            resultLabel.setText(checkAgainstRegex(table, allTransitions, string) ? "Accepted" : "Not Accepted");
        } else {
            resultLabel.setText("you should select the NDFSA file");
        }
    }

    public ConcurrentHashMap readFile(ArrayList<Character> allTransitions, File inputFile) {
        ConcurrentHashMap<String, State> table = new ConcurrentHashMap<>();
        try {
            Scanner scan = new Scanner(inputFile);
            int index = 1;
            String line;
            while (scan.hasNext()) {
                line = scan.nextLine();
                if (index == 1) {
                    String[] states = line.split(" ");
                    boolean start = false;
                    for (String s : states) {
                        State state = new State(s);
                        if (!start) {
                            state.setStartState(true);
                            start = true;
                        }
                        table.put(s, state);
                    }
                } else if (index == 2) {
                    String[] finalStates = line.split(" ");
                    for (String s : finalStates) {
                        table.get(s).setFinalState(true);
                    }
                } else if (index == 3) {
                    String[] row = line.split(" ");
                    for (String s : row) {
                        allTransitions.add(s.charAt(0));
                    }
                } else {
                    String[] row = line.split(" ");
                    String from = row[0];
                    String to = row[1];
                    Character transition = row[2].charAt(0);
                    ConcurrentHashMap<Character, HashSet<State>> transitions = table.get(from).getTransitions();
                    allTransitions.forEach(character -> transitions.computeIfAbsent(character, k -> new HashSet<>()));
                    transitions.get(transition).add(table.get(to));
                }
                ++index;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return table;
    }

    public void removeNonDeterministic(ConcurrentHashMap<String, State> table) {
        boolean flag;
        Iterator it = table.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, State> entry = (Map.Entry) it.next();
            String string = entry.getKey();
            State state = entry.getValue();
            for (Map.Entry<Character, HashSet<State>> entry1 : state.getTransitions().entrySet()) {
                flag = false;
                Set<State> states = entry1.getValue();
                if (states.size() > 1) {
                    StringBuilder name = new StringBuilder();
                    ArrayList<String> l = new ArrayList();
                    for (State sta : states) {
                        l.add(sta.getName());
                        if (sta.isFinalState()) {
                            flag = true;
                        }
                    }
                    Collections.sort(l);
                    for (String s : l) {
                        name.append(s);
                    }
                    State newState;
                    String key = new String(name);
                    if (!table.containsKey(key)) {
                        newState = new State(name.toString());
                        ConcurrentHashMap<Character, HashSet<State>> trns = newState.getTransitions();
                        for (State state1 : states) {
                            state1.getTransitions().forEach((character1, states1) -> {
                                if (!trns.containsKey(character1)) {
                                    trns.put(character1, new HashSet<>());
                                }
                                trns.get(character1).addAll(states1);
                            });
                        }
                        if (flag) {
                            newState.setFinalState(true);
                        }
                        table.put(name.toString(), newState);
                    } else {
                        newState = table.get(name.toString());
                    }
                    states.clear();
                    states.add(newState);
                }
            }
        }
    }

    public void removeLambdas(ConcurrentHashMap<String, State> table) {
        table.forEach((s, state) -> {
            remove(state);
            table.forEach((s1, state1) -> state1.setVisited(false));
        });
        table.forEach((s, state) -> state.getTransitions().remove('@'));
        allTransitions.removeIf(character -> character.equals('@'));
    }

    public void remove(State state) {
        if (!state.getTransitions().containsKey('@') || state.isVisited()) {
            return;
        }
        state.setVisited(true);
        state.getTransitions().get('@').forEach(to -> {
            if (!to.isVisited()) {
                remove(to);
                to.getTransitions().forEach((character, states) -> {
                    if (character != '@') {
                        Set<State> set = state.getTransitions().get(character);
                        set.addAll(states);
                    }
                });
                if (to.isFinalState()) {
                    state.setFinalState(true);
                }
            }
        });
    }

    public void removeNonAccessible(ConcurrentHashMap<String, State> table) {
        State startState = null;
        startState = table.values().stream().filter(state -> state.isStartState()).findFirst().get();
        setAccessible(startState);
        table.forEach((s, state) -> {
            if (!state.isVisited()) {
                table.remove(s);
            }
        });
    }

    public void setAccessible(State state) {
        if (state.isVisited())
            return;
        state.setVisited(true);
        state.getTransitions().forEach((character, states) -> {
            if (states.size() == 0) {
                return;
            } else {
                states.forEach(state1 -> setAccessible(state1));
            }
        });
    }

    public void mergeEquivalent(ConcurrentHashMap<String, State> table,
                                ArrayList<Character> allTransitions) {
        allTransitions.removeIf(character -> character.equals('@'));
        ConcurrentHashMap<String, State> feasiblePairs = new ConcurrentHashMap<>();
        getFeasiblePairs(table, allTransitions, feasiblePairs);
        removeNonEquivalent(feasiblePairs);
        ConcurrentHashMap<String, ArrayList<String>> equivalents = new ConcurrentHashMap<>();
        feasiblePairs.forEach((s, state) -> {
            String[] pairs = s.split(",");
            String first = pairs[0];
            String second = pairs[1];
            if (equivalents.containsKey(first)) {
                equivalents.get(first).add(second);
            } else if (equivalents.containsKey(second)) {
                equivalents.get(second).add(first);
            } else {
                ArrayList<String> eq = new ArrayList<>();
                eq.add(second);
                equivalents.put(first, eq);
            }
        });
        equivalents.forEach((s, strings) -> {
            strings.forEach(s1 -> {
                if (table.get(s1).isStartState()) {
                    table.get(s).setStartState(true);
                }
                table.remove(s1);
                table.forEach((s2, state) -> {
                    state.getTransitions().forEach((character, states) -> {
                        states.forEach(state1 -> {
                            if (state1.getName().equals(s1)) {
                                states.remove(state1);
                                states.add(table.get(s));
                            }
                        });
                    });
                });
            });
        });
    }

    public void getFeasiblePairs(ConcurrentHashMap<String, State> table,
                                 ArrayList<Character> allTransitions,
                                 ConcurrentHashMap<String, State> feasiblePairs) {
        ArrayList<String> finalKeys = new ArrayList<>();
        ArrayList<String> nonFinalKeys = new ArrayList<>();
        table.forEach((s, state) -> {
            if (state.isFinalState()) {
                finalKeys.add(s);
            } else {
                nonFinalKeys.add(s);
            }
        });
        for (int i = 0; i < nonFinalKeys.size(); ++i) {
            for (int j = i + 1; j < nonFinalKeys.size(); ++j) {
                AtomicBoolean flag = new AtomicBoolean(true);
                int finalJ = j;
                int finalI = i;
                table.get(nonFinalKeys.get(finalI)).getTransitions().forEach((character, states) -> {
                    if (states.size() != table.get(nonFinalKeys.get(finalJ)).getTransitions().get(character).size()) {
                        flag.set(false);
                    }
                });
                if (flag.get()) {
                    String name = "";
                    if (nonFinalKeys.get(finalI).compareTo(nonFinalKeys.get(finalJ)) < 0) {
                        name = nonFinalKeys.get(finalI) + "," + nonFinalKeys.get(finalJ);
                    } else {
                        name = nonFinalKeys.get(finalJ) + "," + nonFinalKeys.get(finalI);
                    }
                    State state = new State(name);
                    state.setTransitions(new ConcurrentHashMap<>());
                    ConcurrentHashMap<Character, HashSet<State>> transitions = state.getTransitions();
                    allTransitions.forEach(character -> transitions.computeIfAbsent(character, k -> new HashSet<>()));
                    for (Character transition : allTransitions) {
                        state.getTransitions().get(transition).addAll(table.get(nonFinalKeys.get(finalI)).getTransitions().get(transition));
                        state.getTransitions().get(transition).addAll(table.get(nonFinalKeys.get(finalJ)).getTransitions().get(transition));
                    }
                    feasiblePairs.put(state.getName(), state);
                }
            }
        }
        for (int i = 0; i < finalKeys.size(); ++i) {
            for (int j = i + 1; j < finalKeys.size(); ++j) {
                AtomicBoolean flag = new AtomicBoolean(true);
                int finalJ = j;
                int finalI = i;
                table.get(finalKeys.get(finalI)).getTransitions().forEach((character, states) -> {
                    if (states.size() != table.get(finalKeys.get(finalJ)).getTransitions().get(character).size()) {
                        flag.set(false);
                    }
                });
                if (flag.get()) {
                    String name = "";
                    if (nonFinalKeys.get(finalI).compareTo(nonFinalKeys.get(finalJ)) < 0) {
                        name = nonFinalKeys.get(finalI) + "," + nonFinalKeys.get(finalJ);
                    } else {
                        name = nonFinalKeys.get(finalJ) + "," + nonFinalKeys.get(finalI);
                    }
                    State state = new State(name);
                    state.setTransitions(new ConcurrentHashMap<>());
                    ConcurrentHashMap<Character, HashSet<State>> transitions = state.getTransitions();
                    allTransitions.forEach(character -> transitions.computeIfAbsent(character, k -> new HashSet<>()));
                    for (Character transition : allTransitions) {
                        state.getTransitions().get(transition).addAll(table.get(finalKeys.get(finalI)).getTransitions().get(transition));
                        state.getTransitions().get(transition).addAll(table.get(finalKeys.get(finalJ)).getTransitions().get(transition));
                    }
                    feasiblePairs.put(state.getName(), state);
                }
            }
        }

    }

    public void removeNonEquivalent(ConcurrentHashMap<String, State> feasiblePairs) {
        feasiblePairs.forEach((s, state) -> {
            state.getTransitions().forEach((character, states) -> {
                boolean flag = false;
                if (states.size() > 1) {
                    List<String> lname = new ArrayList<>();
                    states.forEach(state1 -> lname.add(state1.getName()));
                    String name = "";
                    if (lname.get(0).compareTo(lname.get(1)) < 0) {
                        name = lname.get(0) + "," + lname.get(1);
                    } else {
                        name = lname.get(1) + "," + lname.get(0);
                    }
                    if (!feasiblePairs.containsKey(name)) {
                        flag = true;
                    } else {
                        if (feasiblePairs.get(name).isVisited()) {
                            flag = true;
                        }
                    }
                }
                if (flag && !state.isVisited()) {
                    state.setVisited(true);
                }
            });
        });
        feasiblePairs.forEach((s, state) -> {
            if (state.isVisited()) {
                feasiblePairs.remove(state);
            }
        });
    }

    public void printTable(ConcurrentHashMap<String, State> table) {
        AtomicReference<String> empty = new AtomicReference<>();
        AtomicBoolean flag = new AtomicBoolean(true);
        table.forEach((s, state) -> {
            if (flag.get()) {
                System.out.print("       ");
                state.getTransitions().forEach((character, states) -> {
                    System.out.printf("%-7s | ", character);
                });
                System.out.println();
                System.out.println("------------------------------");
                flag.set(false);
            }
            System.out.printf("%4s | ", s);
            state.getTransitions().forEach((character, states) -> {
                empty.set("       ");
                states.forEach(state1 -> {
                    System.out.printf("%s ", state1.getName());
                });
                empty.set(empty.get().substring(states.size()));
                System.out.printf("%s | ", empty);
            });
            System.out.println();
        });
    }

    public void populateTable() {
        tableView.getColumns().clear();
        tableView.getItems().clear();
        TableColumn<String, String> column = new TableColumn<>("State");
        column.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(column);
        int n = allTransitions.size();
        TableColumn<String, String>[] columns = new TableColumn[n];
        ArrayList<Character> trns=new ArrayList<>();
        table.forEach((s, state) -> state.getTransitions().forEach((character, states) -> trns.add(character)));
        for (int i = 0; i < n; ++i) {
            columns[i] = new TableColumn<String, String>(trns.get(i) + "");
            columns[i].setCellValueFactory(new PropertyValueFactory<>("v"+(i+1)));
            tableView.getColumns().add(columns[i]);
        }
        table.forEach((s, state) -> {
            AtomicReference<String> val= new AtomicReference<>("");
            List<String> list=new ArrayList<>();
            state.getTransitions().forEach((character, states) -> {
                val.set("");
                states.forEach(state1 -> val.set(state1.isFinalState()?val.get()+state1.getName()+"* ":val.get()+state1.getName()+" "));
                list.add(val.get());
            });
            Row row= RowFactory.getInstance(n,state.isFinalState()?state.getName()+"*":state.getName(),list);
            tableView.getItems().add(row);
        });

//        column.setCellValueFactory(new PropertyValueFactory<>("hash"));
//        String string="layth";
//        tableView.getColumns().add(column);
//        tableView.getItems().
    }

    public boolean checkAgainstRegex(ConcurrentHashMap<String, State> table,
                                     ArrayList<Character> allTransitions, String string) {
        AtomicReference<State> currentState = new AtomicReference<>();
        currentState.set(table.values().stream().filter(state -> state.isStartState()).findFirst().get());
        boolean flag = true;
        AtomicBoolean stuck = new AtomicBoolean(false);
        AtomicBoolean result = new AtomicBoolean(false);
        for (int i = 0; i < string.length(); ++i) {
            Character c = string.charAt(i);
            if (Character.isDigit(c) && !allTransitions.contains('d')) {
                return false;
            } else if (Character.isLetter(c) && !allTransitions.contains('l')) {
                return false;
            } else if (!Character.isDigit(c) && !Character.isLetter(c)) {
                return false;
            } else {
                Character next;
                int finalI = i;
                currentState.get().getTransitions().forEach((character, states) -> {
                    if (Character.isDigit(c) && character == 'd') {
                        if (states.size() == 0) {
                            stuck.set(true);
                        } else {
                            currentState.set(currentState.get().getTransitions().get(character).stream().findFirst().get());
                        }
                    } else if (Character.isLetter(c) && character == 'l') {
                        if (states.size() == 0) {
                            stuck.set(true);
                        } else {
                            currentState.set(currentState.get().getTransitions().get(character).stream().findFirst().get());
                        }
                    }
                    if (currentState.get().isFinalState() && finalI == string.length() - 1) {
                        result.set(true);
                    }
                });
            }
        }
        return flag && !stuck.get() && result.get();
    }

}
