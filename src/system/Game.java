package system;

import models.Cell;
import service.Grid;
import service.impls.GridImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static errors.ErrorMessages.INVALID_CELL;
import static java.util.stream.Collectors.toList;
import static models.enums.Color.GREEN;
import static models.enums.Color.RED;

public class Game implements Runnable {
    private static final String WELCOME_MESSAGE = "Hello, please enter correct input!";
    private static final int GREEN_VALUE = 1;
    private static final int RED_VALUE = 0;
    private static final List<Integer> RED_NUMBERS_FOR_CHANGE = new ArrayList<>(List.of(3, 6));
    private static final List<Integer> GREEN_NUMBERS_FOR_CHANGE = new ArrayList<>(List.of(0, 1, 4, 5, 7, 8));

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private Grid grid;
    private int targetRow;
    private int targetCol;
    private int generations;
    private int greenCellsCounter = 0;

    @Override
    public void run() {
        System.out.println(WELCOME_MESSAGE);
        try {
            setGrid();
            getTargetAndGenerations();
            play();
            System.out.printf("The result is: %d", greenCellsCounter);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    private void play() {
        //check for ZeroGeneration
        if (checkTarget()) {
            greenCellsCounter++;
        }

        for (int i = 0; i < generations; i++) {
            grid.setGrid(formNextGeneration());
            if (checkTarget()) {
                greenCellsCounter++;
            }
        }
    }

    private List<Cell> formNextGeneration() {
        List<Cell> resultGrid = grid.getGrid().stream().map(Cell::new).collect(toList());

        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                applyRules(resultGrid, row, col);
            }
        }

        return resultGrid;
    }

    private void applyRules(List<Cell> resultGrid, int row, int col) {
        Cell cell = getCell(resultGrid, row, col);
        int greenCells = countSurroundingGreenCells(cell);

        if (cell.getColor() == RED) {
            if (RED_NUMBERS_FOR_CHANGE.contains(greenCells)) {
                cell.setValue(GREEN_VALUE);
                cell.setColor(GREEN);
            }
        } else if (cell.getColor() == GREEN) {
            if (GREEN_NUMBERS_FOR_CHANGE.contains(greenCells)) {
                cell.setValue(RED_VALUE);
                cell.setColor(RED);
            }
        }
    }

    private int countSurroundingGreenCells(Cell cell) {
        int surroundings = 0;

        for (int rowIndex = -1; rowIndex <= 1; rowIndex++) {
            for (int colIndex = -1; colIndex <= 1; colIndex++) {
                int currentRow = cell.getRow() + rowIndex;
                int currentCol = cell.getCol() + colIndex;

                if (isInBounds(currentRow, currentCol)) {
                    if (rowIndex != 0 || colIndex != 0) {
                        surroundings += getCell(grid.getGrid(), currentRow, currentCol).getValue();
                    }
                }
            }
        }
        return surroundings;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getCols();
    }

    private boolean checkTarget() {
        return getCell(grid.getGrid(), targetRow, targetCol).getColor() == GREEN;
    }

    private Cell getCell(List<Cell> grid, int row, int col) {
        return grid.stream().filter(c -> c.getRow() == row && c.getCol() == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_CELL));
    }

    private void setGrid() throws IOException {
        int[] gridMeasures = Arrays.stream(READER.readLine().split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();

        grid = new GridImpl(gridMeasures[1], gridMeasures[0]);

        for (int row = 0; row < grid.getRows(); row++) {
            int[] currentRow = Arrays.stream(READER.readLine().split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            grid.addRow(row, currentRow);
        }
    }

    private void getTargetAndGenerations() throws IOException {
        int[] tokens = Arrays.stream(READER.readLine().split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();
        targetCol = tokens[0];
        targetRow = tokens[1];
        generations = tokens[2];
    }
}
