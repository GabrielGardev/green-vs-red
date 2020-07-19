package service.impls;

import models.Cell;
import service.Grid;
import java.util.ArrayList;
import java.util.List;

import static errors.ErrorMessages.*;

public class GridImpl implements Grid {
    private final int rows;
    private final int cols;
    private List<Cell> grid;

    public GridImpl(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        setGrid();
    }

    private void setGrid(){
        this.grid = new ArrayList<>();
        checkInputData();
    }

    @Override
    public void addRow(int rowNumber, int[] currentRow) {
        for (int col = 0; col < currentRow.length; col++) {
            Cell cell = new Cell(rowNumber, col, currentRow[col]);
            grid.add(cell);
        }
    }

    public List<Cell> getGrid() {
        return grid;
    }

    public void setGrid(List<Cell> grid) {
        this.grid = grid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    //this method checks input rows and cols for zero or negative input
    private void checkInputData() {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException(INVALID_INPUT);
        }
    }
}