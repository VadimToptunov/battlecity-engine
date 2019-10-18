package com.codenjoy.dojo.battlecity.client;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;

import java.util.ArrayList;
import java.util.List;

public class Shooter {

    private BoardLayout boardLayout;

    public Shooter(BoardLayout boardLayout) {
        this.boardLayout = boardLayout;
    }


    private boolean isSameLineEnemy(Point bot, Point enemy) {
        if (bot.getX() == enemy.getX() || bot.getY() == enemy.getY()) {
            return true;
        }
        return false;
    }

    public boolean isLeft(Point bot, Point enemy) {
        if (bot.getX() > enemy.getX()) {
            return true;
        }
        return false;
    }

    public boolean isRight(Point bot, Point enemy) {
        if (bot.getX() < enemy.getX()) {
            return true;
        }
        return false;
    }

    public boolean isUp(Point bot, Point enemy) {
        if (bot.getY() > enemy.getY()) {
            return true;
        }
        return false;
    }

    public boolean isDown(Point bot, Point enemy) {
        if (bot.getY() < enemy.getY()) {
            return true;
        }
        return false;
    }

    public Direction locateEnemy(Point nearestEnemy) {
        Point point = boardLayout.board.getMe();
        boolean isSameLineEnemy = isSameLineEnemy(point, nearestEnemy);
        if (isSameLineEnemy && isDown(point, nearestEnemy)) {
            point.change(Direction.DOWN);
            return Direction.DOWN;
        } else if (isSameLineEnemy && isUp(point, nearestEnemy)) {
            point.change(Direction.UP);
            return Direction.UP;
        } else if (isSameLineEnemy && isLeft(point, nearestEnemy)) {
            point.change(Direction.LEFT);
            return Direction.LEFT;
        } else if (isSameLineEnemy && isRight(point, nearestEnemy)) {
            point.change(Direction.RIGHT);
            return Direction.RIGHT;
        } else {
            point.change(Direction.random());
            return Direction.STOP;
        }
    }


    public List<Point> getSameLineEnemies(){
        Point bot = boardLayout.board.getMe();
        List <Point> allEnemies = boardLayout.board.getEnemies();
        List<Point> sameLineEnemies = new ArrayList<>();
        for (Point enemy : allEnemies){
            if(bot.getX() == enemy.getX() || bot.getY() == enemy.getY()){
                sameLineEnemies.add(enemy);
            }
        }
        return sameLineEnemies;
    }
}