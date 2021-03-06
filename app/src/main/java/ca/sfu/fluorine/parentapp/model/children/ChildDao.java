package ca.sfu.fluorine.parentapp.model.children;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.BaseDao;

/**
 * Represents a data access object (DAO) to manipulate data about children in the database
 */
@Dao
public abstract class ChildDao implements BaseDao<Child> {
	@Query("SELECT * FROM children ORDER BY createdTime DESC")
	public abstract LiveData<List<Child>> getAllChildren();

	// By the magic of SQL...
	@Query("SELECT children.* FROM children LEFT JOIN coin_result " +
			"ON child_id == selected_child_id " +
			"GROUP BY child_id ORDER BY MAX(coin_result.dateTimeOfFlip)")
	public abstract LiveData<List<Child>> getAllChildrenOrderByRecentCoinFlips();

	@Nullable
	@Query("SELECT * FROM children WHERE child_id = :id LIMIT 1")
	public abstract Child getChildById(int id);

	@Nullable
	@Query("SELECT * FROM children WHERE createdTime > :createdTime " +
			"ORDER BY createdTime LIMIT 1")
	abstract Child getFirstChildAfterCreationTime(long createdTime);

	@Query("SELECT * FROM children " +
			"ORDER BY createdTime LIMIT 1")
	@Nullable
	abstract Child getFirstCreatedChild();

	// Get the next child according to their creation time
	// if the last child's turn is done, then returns the first created child
	@Transaction
	public Child getNextChildId(Child child) {
		Child nextChild = getFirstChildAfterCreationTime(child.getCreatedTime());
		if (nextChild == null) {
			nextChild = getFirstCreatedChild();
		}
		return (nextChild == null) ? Child.getUnspecifiedChild() : nextChild;
	}
}
