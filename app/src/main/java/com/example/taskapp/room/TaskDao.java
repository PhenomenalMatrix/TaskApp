package com.example.taskapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task order by title asc ")
    List<Task> sort();


//  // worked sql
//    @Query("DELETE from task WHERE id IN (:idList)")
//    void deleteByIdList(int idList);
//  // worked sql
//    @Query("UPDATE task Set title = :newTitle, `desc` = :newDesc WHERE id IN (:idList)")
//    void updateSalaryByIdList(int idList ,String newTitle, String newDesc);

}
