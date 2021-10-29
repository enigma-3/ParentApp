package ca.sfu.fluorine.parentapp.view.timeout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTimeoutFinishBinding;
import ca.sfu.fluorine.parentapp.view.ChildrenFragmentDirections;

/**
 * Represents the end screen when the timer reaches 0
 */
public class TimeoutFinishFragment extends Fragment {
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return FragmentTimeoutFinishBinding
				.inflate(inflater, container, false)
				.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Add listener to the button
		Button dismissButton = FragmentTimeoutFinishBinding.bind(view).button;
		dismissButton.setOnClickListener(btnView ->
			Navigation.findNavController(view).navigate(R.id.return_to_timeout)
		);
	}

	// TODO: Play music and vibrate the phone when this fragment appear
}