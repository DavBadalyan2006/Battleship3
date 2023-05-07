package com.example.battleship.ui.home;

import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.battleship.R;
import com.example.battleship.gamelogic.SearchingActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button findMatchButton = root.findViewById(R.id.find_match_button);

        findMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchingActivity();
            }
        });

        return root;
    }

    private void openSearchingActivity() {
        Intent intent = new Intent(getActivity(), SearchingActivity.class);
        startActivity(intent);
    }
}
