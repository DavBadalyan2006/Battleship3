package com.example.battleship.gamelogic;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.battleship.R;

public class GameActivity extends AppCompatActivity {
    private GameState gameState;
    private BoardAdapter playerBoardAdapter;
    private BoardAdapter opponentBoardAdapter;
    private GridView playerBoardGridView;
    private GridView opponentBoardGridView;
    private CountDownTimer setupTimer;
    private CountDownTimer gameTimer;
    private TextView setupTimerTextView;
    private TextView gameTimerTextView;
    private Button endTurnButton;
    private TextView gameStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameState = new GameState();

        playerBoardGridView = findViewById(R.id.player_board_grid_view);
        opponentBoardGridView = findViewById(R.id.opponent_board_grid_view);
        setupTimerTextView = findViewById(R.id.setup_timer_text_view);
        gameTimerTextView = findViewById(R.id.game_timer_text_view);
        gameStatusTextView = findViewById(R.id.game_status_text_view);
        endTurnButton = findViewById(R.id.end_turn_button);

        setUpGameBoards();
        setUpTimers();
    }

    private void setUpGameBoards() {
        // Initialize the game boards
        gameState = new GameState();
        playerBoardAdapter = new BoardAdapter(this, gameState.getPlayerBoard());
        opponentBoardAdapter = new BoardAdapter(this, gameState.getOpponentBoard());

        // Set up adapters for the GridViews
        playerBoardGridView.setAdapter(playerBoardAdapter);
        opponentBoardGridView.setAdapter(opponentBoardAdapter);

        // Update the UI
        playerBoardAdapter.notifyDataSetChanged();
        opponentBoardAdapter.notifyDataSetChanged();

    }

    private void setUpTimers() {
        // Initialize the setup timer
        setupTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setupTimerTextView.setText("Time remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                if (!allShipsPlaced()) {
                    setupTimerTextView.setText("Setup time is over and not all ships have been placed. Game aborted.");
                    // End the activity or navigate to another activity, e.g., main menu
                    finish();
                } else {
                    setupTimerTextView.setText("Setup time is over!");
                    // Proceed to the game phase, e.g., start the game timer and enable user interaction
                }
            }
        };

        // Initialize the game timer
        gameTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameTimerTextView.setText("Time remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                gameTimerTextView.setText("Time's up!");
                gameState.switchTurns(); // Handles end of turn
                updateUI();
                gameTimer.start(); // Restart the timer for the next turn
            }
        };

        setupTimer.start();
    }

    private int[] shipCount = new int[]{0, 0, 0, 0};

    private boolean allShipsPlaced() {
        int[] requiredShipCount = new int[]{4, 3, 2, 1};
        for (int i = 0; i < shipCount.length; i++) {
            if (shipCount[i] != requiredShipCount[i]) {
                return false;
            }
        }
        return true;
    }

    private void updateUI() {
        playerBoardAdapter.notifyDataSetChanged();
        opponentBoardAdapter.notifyDataSetChanged();
        gameStatusTextView.setText(gameState.isPlayerTurn() ? "Your turn" : "Opponent's turn");
    }
}



