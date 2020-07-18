package service;

import models.Cell;

import java.util.List;

public interface Grid {
    List<Cell> getGrid();
    void setGrid(List<Cell> grid);
    int getRows();
    int getCols();
    void addRow(int rowNumber, int[] nums);
}
