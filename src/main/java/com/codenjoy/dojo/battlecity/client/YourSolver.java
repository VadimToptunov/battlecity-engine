package com.codenjoy.dojo.battlecity.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    private Strategy strategy;
    private static final String SERVER_URI =
            "http://dojorena.io/codenjoy-contest/board/player/45kru5h2flny6l4ryxk9?code=4294191349695056702";
//            "http://codenjoy.com/codenjoy-contest/board/player/gstxdkin95pe92iikhku?code=7522407875143803596";

    public YourSolver(Dice dice) {
        strategy = new Strategy(dice);

    }

    @Override
    public String get(Board board) {
        try {
            return strategy.chaseAndKill(board);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Direction.STOP.toString();
        }
    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // paste here board page url from browser after registration
                SERVER_URI,
                new YourSolver(new RandomDice()),
                new Board());
        }
    }
