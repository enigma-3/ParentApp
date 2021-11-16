package ca.sfu.fluorine.parentapp.view.children;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;

public class EditChildActivity extends AddChildActivity {
	// For intent data
	private static final String CHILD_ID = "childIndex";
	public static final int DEFAULT = -1;

	private Child child;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.edit_child);

		// Populate the data
		int childId = getIntent().getIntExtra(CHILD_ID, DEFAULT);
		List<Child> children = database.childDao().getChildById(childId);
		if (!children.isEmpty()) {
			child = children.get(0);
			// Remove watcher before set text
			binding.editTextFirstName.removeTextChangedListener(watcher);
			binding.editTextLastName.removeTextChangedListener(watcher);

			binding.editTextFirstName.setText(child.getFirstName());
			binding.editTextLastName.setText(child.getLastName());

			// Remove watcher before set text
			binding.editTextFirstName.addTextChangedListener(watcher);
			binding.editTextLastName.addTextChangedListener(watcher);

			// TODO: Set up the icon (if possible)
		}

		// Activate more buttons
		binding.buttonAddChild.setOnClickListener(editChildrenDialogListener);
		binding.buttonDeleteChild.setVisibility(View.VISIBLE);
		binding.buttonDeleteChild.setOnClickListener(deleteChildDialogListener);
	}

	public static Intent makeIntent(Context context, int childId) {
		Intent intent = new Intent(context, EditChildActivity.class);
		intent.putExtra(CHILD_ID, childId);
		return intent;
	}

	private final View.OnClickListener editChildrenDialogListener = (btnView) -> makeConfirmDialog(
			R.string.edit_child,
			R.string.edit_child_confirm,
			(dialogInterface, i) -> {
				String firstName = binding.editTextFirstName.getText().toString();
				String lastName = binding.editTextLastName.getText().toString();
				child.updateName(firstName, lastName);
				persistIconData(child);
				database.childDao().updateChild(child);
				finish();
			});

	private final View.OnClickListener deleteChildDialogListener = (btnView) -> makeConfirmDialog(
			R.string.delete_child,
			R.string.delete_child_confirm,
			(dialogInterface, i) -> {
				database.childDao().deleteChild(child);
				finish();
			});
}
