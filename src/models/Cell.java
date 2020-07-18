package models;

import models.enums.Color;

public class Cell {
    private int row;
    private int col;
    private int value;
    private Color color;

    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.color = getColor(value);
    }

    //this constructor helps for cloning cells
    public Cell(Cell cell){
        this.row = cell.row;
        this.col = cell.col;
        this.value = cell.value;
        this.color = cell.color;
    }

    private Color getColor(int value) {
        return value == 1 ? Color.GREEN : Color.RED;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
