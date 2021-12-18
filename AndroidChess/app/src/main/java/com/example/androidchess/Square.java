package com.example.androidchess;

public class Square {
    int file;
    int rank;

    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return (file == ((Square) obj).file && rank == ((Square) obj).rank);
    }
}
