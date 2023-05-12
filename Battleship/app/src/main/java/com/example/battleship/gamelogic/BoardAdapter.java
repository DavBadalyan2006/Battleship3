package com.example.battleship.gamelogic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.battleship.R;

public class BoardAdapter extends BaseAdapter {
    private Context context;
    private Board board;
    private LayoutInflater layoutInflater;

    public BoardAdapter(Context context, Board board) {
        this.context = context;
        this.board = board;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Board.BOARD_SIZE * Board.BOARD_SIZE;
    }

    @Override
    public Object getItem(int position) {
        int row = position / Board.BOARD_SIZE;
        int col = position % Board.BOARD_SIZE;
        return board.getCell(row, col);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.board_cell, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.cell_image);
        int cellValue = (int) getItem(position);

        // Set the image for the cell based on its value
        switch (cellValue) {
            case Board.EMPTY:
                imageView.setImageResource(R.drawable.empty_cell);
                break;
            case Board.SHIP:
                imageView.setImageResource(R.drawable.ship_cell);
                break;
            case Board.HIT:
                imageView.setImageResource(R.drawable.hit_cell);
                break;
            case Board.MISS:
                imageView.setImageResource(R.drawable.miss_cell);
                break;
        }

        return convertView;
    }
}