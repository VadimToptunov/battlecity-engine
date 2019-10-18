package com.codenjoy.dojo.battlecity.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class LayoutAnalyser {
    private int currentTick;
    private LinkedList<Board> history = new LinkedList<>();
    private Collection<Tank> enemyVehicles = new ArrayList<>();
    private Board board;

    public BoardLayout getBoardLayout() {
        BoardLayout boardLayout = new BoardLayout();
        boardLayout.board = board;
        boardLayout.tick = currentTick;
        boardLayout.enemyVehicles = enemyVehicles;
        boardLayout.size = board.size();
        return boardLayout;
    }

    public void processNext(Board board) {
        currentTick++;
        this.board = board;
        history.addFirst(board);
        if (history.size() > 10)
            history.removeLast();
    }
}