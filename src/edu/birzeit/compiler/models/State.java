package edu.birzeit.compiler.models;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class State {

    private String name;
    private boolean finalState;
    private boolean startState;
    private boolean visited;
    private ConcurrentHashMap<Character, HashSet<State>> transitions;

    public State() {
    }

    public State(String name) {
        this.name = name;
        this.finalState = false;
        this.startState = false;
        this.transitions = new ConcurrentHashMap<Character, HashSet<State>>();
    }

    public State(String name, boolean finalState, boolean startState, ConcurrentHashMap<Character, HashSet<State>> transitions, boolean visited) {
        this.name = name;
        this.finalState = finalState;
        this.startState = startState;
        this.transitions = transitions;
        this.visited = visited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }

    public boolean isStartState() {
        return startState;
    }

    public void setStartState(boolean startState) {
        this.startState = startState;
    }

    public ConcurrentHashMap<Character, HashSet<State>> getTransitions() {
        return transitions;
    }

    public void setTransitions(ConcurrentHashMap<Character, HashSet<State>> transitions) {
        this.transitions = transitions;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", finalState=" + finalState +
                ", startState=" + startState +
                ", visited=" + visited +
                '}';
    }

}
