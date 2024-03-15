package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TaskEntity flashcard);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<TaskEntity> tasks);

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskEntity find(int id);

    @Query("SELECT * FROM tasks ORDER BY sort_order")
    List<TaskEntity> findAll();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskEntity> findAsLiveData(int id);

    @Query("SELECT * FROM tasks ORDER BY sort_order")
    LiveData<List<TaskEntity>> findAllAsLiveData();

    @Query("SELECT COUNT(*) FROM tasks")
    int count();

    @Query("SELECT MIN(sort_order) FROM tasks")
    int getMinSortOrder();

    @Query("SELECT MAX(sort_order) FROM tasks")
    int getMaxSortOrder();

    @Query("UPDATE tasks SET sort_order = sort_order + :by WHERE sort_order >= :from AND sort_order <= :to")
    void shiftSortOrders(int from, int to, int by);

    @Transaction
    default int append(TaskEntity taskEntity) {
        var newTask = taskEntity.withSortOrder(getMaxSortOrder() + 1);
        return Math.toIntExact(insert(newTask));
    }

    @Transaction
    default int prepend(TaskEntity taskEntity) {
        shiftSortOrders(getMinSortOrder(), getMaxSortOrder(), 1);
        var newTask = taskEntity.withSortOrder(getMinSortOrder() - 1);
        return Math.toIntExact(insert(newTask));
    }

    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(int id);
}
