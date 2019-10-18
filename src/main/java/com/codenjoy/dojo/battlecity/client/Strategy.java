package com.codenjoy.dojo.battlecity.client;

import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;

import java.util.Comparator;
import java.util.List;

public class Strategy {
    private LayoutAnalyser layoutAnalyser;
    private Dice dice;

    public Strategy(Dice dice) {
        this.layoutAnalyser = new LayoutAnalyser();
        this.dice = dice;
    }

    public String chaseAndKill(Board board) {
        layoutAnalyser.processNext(board);
        BoardLayout boardLayout = layoutAnalyser.getBoardLayout();
        Shooter shooter = new Shooter(boardLayout);
        List<Point> sameLineEnemies = shooter.getSameLineEnemies();
        Point bot = boardLayout.board.getMe();
        Point nearestEnemy = board.getEnemies().stream().min(Comparator.comparing(enemy -> Math.floor(enemy.distance(bot))))
                .get();
        for (Point enemy : sameLineEnemies) {
            bot.move(nearestEnemy);
            Direction shootDirection = shooter.locateEnemy(enemy);
            double distanceToEnemy = boardLayout.board.getMe().distance(enemy);
            if (shootDirection != Direction.STOP && distanceToEnemy < 20){
                if (isLookingAtMe(enemy)){
                    return setDirection(enemy).toString() + ", " + Direction.ACT.toString();
                }
                bot.change(shootDirection);
                bot.move(nearestEnemy);
                boardLayout.board.goToFree(board.getFree());
                return shootDirection.toString() + ", " + Direction.ACT.toString();
            }
            return Direction.random().toString() + ", " + Direction.ACT.toString();
        }
        board.goToFree(board.getFree());
        return  Direction.random().toString() + ", " + Direction.ACT.toString();
    }

    private boolean isLookingAtMe(Point enemy){
        BoardLayout boardLayout = layoutAnalyser.getBoardLayout();
        Board board = boardLayout.board;
        Direction myDirection = board.getDirectionOf(board.getMe().getX(), board.getMe().getY());
        Direction enemyDirection = board.getDirectionOf(enemy.getX(), enemy.getY());
        return myDirection == enemyDirection;
    }

    private Direction setDirection(Point enemy){
        Board board = layoutAnalyser.getBoardLayout().board;
        Point me = board.getMe();
        Direction enemyDirection = getDirection(me, enemy); //board.getDirectionOf(enemy.getX(), enemy.getY());
        int inverted = board.inversionY(enemy.getY());
        Direction direction = board.getDirectionOf(enemy.getX(), inverted);
        if (isLookingAtMe(enemy)){
            me.change(direction);
        }
        return enemyDirection;
    }

    private Direction getDirection(Point bot, Point enemy){
        BoardLayout boardLayout = layoutAnalyser.getBoardLayout();
        Shooter shooter = new Shooter(boardLayout);
        if (shooter.isUp(bot, enemy)) return Direction.UP;
        if (shooter.isRight(bot, enemy)) return Direction.RIGHT;
        if (shooter.isDown(bot, enemy)) return Direction.DOWN;
        if (shooter.isLeft(bot, enemy)) return Direction.LEFT;
        return Direction.STOP;
    }
}
