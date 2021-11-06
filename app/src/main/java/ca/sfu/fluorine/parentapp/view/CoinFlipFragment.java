package ca.sfu.fluorine.parentapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ca.sfu.fluorine.parentapp.CoinFlipActivity;
import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinFlipHistory;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;


public class CoinFlipFragment extends Fragment {
	private FragmentCoinFlipBinding binding;
	private CoinFlipHistory flipHistory;
	private Context ct;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		flipHistory = CoinFlipHistory.getInstance(requireContext());
		binding = FragmentCoinFlipBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		binding.navigationCoinFlip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent add = new Intent(getContext(), CoinFlipActivity.class);
				startActivity(add);
			}

		});

		binding.listCoinFlip.setAdapter(new CoinHistoryAdapter(ct, this));
		binding.listCoinFlip.setLayoutManager(new LinearLayoutManager(ct));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

	public class CoinHistoryAdapter extends RecyclerView.Adapter<CoinHistoryAdapter.CoinFlipViewHolder>{

		//TODO : create variables to hold our values passed from database.
		private CoinFlipFragment coinFlipFragment;

		public CoinHistoryAdapter(Context ct, CoinFlipFragment coinFlipFragment){
			this.coinFlipFragment = coinFlipFragment;
		}

		public class ViewHolder extends RecyclerView.ViewHolder{

			public ViewHolder(@NonNull View itemView) {
				super(itemView);
			}
		}

		@NonNull
		@Override
		public CoinFlipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return null;
		}

		@Override
		public void onBindViewHolder(@NonNull CoinFlipViewHolder holder, int position) {
			CoinResult result = flipHistory.getCoinFlipAtIndex(position);

			//holder.dateTimeView.setText(result.getDateTimeOfFlip()); // TODO : issue w datetime
			//set child's name in view
			holder.childNameView.setText(result.getWhoPicked().getFirstName());

			// set view if picker win/lose
			holder.didPickerWinView.setText(result.didPickerWin() ? "WIN" : "LOSE" );

			//set image of coin if the flip result is head/tails.
			holder.coinResultView.setImageResource(result.getResultIsHead() ?
					R.drawable.heads : R.drawable.tails);
		}

		@Override
		public int getItemCount() {
			return flipHistory.getCoinFlip().size();
		}

		public class CoinFlipViewHolder extends RecyclerView.ViewHolder {
			CardView coinCardView;
			TextView dateTimeView;
			TextView childNameView;
			TextView didPickerWinView;
			ImageView coinResultView;

			public CoinFlipViewHolder(@NonNull View itemView) {
				super(itemView);

				coinCardView = itemView.findViewById(R.id.coinResultCard);
				dateTimeView = itemView.findViewById(R.id.dateTimeFlip);
				childNameView = itemView.findViewById(R.id.childNameCoinView);
				didPickerWinView = itemView.findViewById(R.id.didPickerWin);
				coinResultView = itemView.findViewById(R.id.imageView);
			}
		}
	}


}