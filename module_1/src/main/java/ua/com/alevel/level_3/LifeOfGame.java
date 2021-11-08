package ua.com.alevel.level_3;

import java.util.Random;

public class LifeOfGame {

    private static final int MAX_NEIGHBOR_DISTANCE = 1;
    private static final int MAX_WORLD_SIZE = 10;
    private static final int MIN_WORLD_SIZE = 5;
    private static final boolean SILENT_MODE = false;

    public LifeOfGame() {
        int steps = 100;
        int x = (int) (Math.random() * MAX_WORLD_SIZE) + MIN_WORLD_SIZE;
        int y = (int) (Math.random() * MAX_WORLD_SIZE) + MIN_WORLD_SIZE;
        boolean[][] cells = initWorld(x, y);
        boolean[][] oldCells = new boolean[x][y];

        System.out.println("The original world: ");
        printWorld(cells);
        int i = 0;
        for (; i < steps && !equalsWorld(cells, oldCells); i++) {
            oldCells = cells;
            cells = nextGeneration(cells);

            if (!SILENT_MODE) {
                System.out.println("Generation " + (i + 1) + ":");
                printWorld(cells);
            }
        }
        if (SILENT_MODE) {
            System.out.println("Generation " + (i + 1) + ":");
            printWorld(cells);
        }
    }

    private boolean[][] initWorld(int x, int y) {
        boolean[][] cells = new boolean[x][y];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cells[i][j] = random.nextBoolean();
            }
        }
        return cells;
    }

    private void printWorld(boolean[][] cells) {
        for (boolean[] cell : cells) {
            for (boolean b : cell) {
                System.out.print(b ? "o " : "x ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean[][] nextGeneration(boolean[][] cells) {
        boolean[][] newCellGeneration = new boolean[cells.length][0];
        for (int i = 0; i < cells.length; i++) {
            newCellGeneration[i] = new boolean[cells[i].length];
            for (int j = 0; j < cells[i].length; j++) {
                newCellGeneration[i][j] = getCellStatus(cells[i][j], getNeighborCount(cells, i, j));
            }
        }
        return newCellGeneration;
    }

    private boolean getCellStatus(boolean cells, int neighborCount) {
        if (cells) {
            return neighborCount >= 2 && neighborCount <= 3;
        } else {
            return neighborCount == 3;
        }
    }

    private int getNeighborCount(boolean[][] cells, int i, int j) {
        int neighborCount = 0;
        int iMax = Math.min(i + MAX_NEIGHBOR_DISTANCE + 1, cells.length);
        for (int ii = Math.max(i - MAX_NEIGHBOR_DISTANCE, 0); ii < iMax; ii++) {
            int jMax = Math.min(j + MAX_NEIGHBOR_DISTANCE + 1, cells[i].length);
            for (int jj = Math.max(j - MAX_NEIGHBOR_DISTANCE, 0); jj < jMax; jj++) {
                if (cells[ii][jj] && (ii != i || jj != j)) {
                    neighborCount++;
                }
            }
        }
        return neighborCount;
    }

    private boolean equalsWorld(boolean[][] newCells, boolean[][] oldCells) {
        for (int i = 0; i < newCells.length; i++) {
            for (int j = 0; j < newCells[i].length; j++) {
                if (newCells[i][j] != oldCells[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
