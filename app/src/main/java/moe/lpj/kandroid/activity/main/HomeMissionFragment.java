package moe.lpj.kandroid.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moe.lpj.kandroid.databinding.FragmentHomeMissionBinding;
import moe.lpj.kandroid.viewmodel.MissionViewModel;

public class HomeMissionFragment extends Fragment {

    private MainActivity mActivity;

    public static boolean isVisible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeMissionBinding homeMissionBinding = FragmentHomeMissionBinding.inflate(inflater, container, false);
        homeMissionBinding.setMission(MissionViewModel.Holder.Instance);
        return homeMissionBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        } else {
            throw new RuntimeException("???");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isVisible = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
