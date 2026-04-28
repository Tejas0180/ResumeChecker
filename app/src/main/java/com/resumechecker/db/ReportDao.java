package com.resumechecker.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.resumechecker.model.ResumeReport;

import java.util.List;

@Dao
public interface ReportDao {

    @Insert
    long insert(ResumeReport report);

    @Query("SELECT * FROM resume_reports ORDER BY timestamp DESC")
    List<ResumeReport> getAll();

    @Query("SELECT * FROM resume_reports WHERE id = :id LIMIT 1")
    ResumeReport getById(int id);

    @Query("DELETE FROM resume_reports WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM resume_reports")
    void deleteAll();
}
