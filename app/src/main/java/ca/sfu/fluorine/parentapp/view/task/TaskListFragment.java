package ca.sfu.fluorine.parentapp.view.task;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTaskListBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.task.TaskWithChild;


/**
 * TaskListFragment.java - Represents a home screen and feed/list of tasks
 *  user can create a new task, or edit existing ones.
 */
public class TaskListFragment extends Fragment {
    private AppDatabase database;
    private FragmentTaskListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getInstance(requireContext());
    }

    @Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentTaskListBinding
				.inflate(inflater, container, false);
		return binding.getRoot();
	}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // floating action button
        binding.buttonAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddTaskActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        List<TaskWithChild> tasks = database.taskDao().getAllTasksWithChildren();
        if (tasks.isEmpty()) {
            binding.taskList.showEmpty();
        } else {
            binding.taskList.useAdapter(new TaskListAdapter(tasks));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {
        private final List<TaskWithChild> tasks;

        public TaskListAdapter(List<TaskWithChild> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(
            @NonNull
                ViewGroup parent,
            int viewType
        ) {
            View view = LayoutInflater.from(requireContext()).inflate(
                R.layout.task_row_layout,
                parent,
                false
            );
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(
            @NonNull
                TaskViewHolder taskHolder,
            int position
        ) {
            // get child object from index
            TaskWithChild task = tasks.get(position);

            taskHolder.populateData(requireContext(), task);

            // make the list item clickable
            taskHolder.itemView.setOnClickListener((View view) -> {
                Intent intent = EditTaskActivity.makeIntent(requireContext(), task.getTask().getId());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }


}
