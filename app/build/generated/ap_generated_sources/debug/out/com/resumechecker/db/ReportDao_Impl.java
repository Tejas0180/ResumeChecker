package com.resumechecker.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.resumechecker.model.ResumeReport;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ResumeReport> __insertionAdapterOfResumeReport;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfResumeReport = new EntityInsertionAdapter<ResumeReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `resume_reports` (`id`,`fileName`,`timestamp`,`resumeScore`,`atsScore`,`targetRole`,`matchedKeywords`,`missingKeywords`,`keywordMatchRatio`,`hasEmail`,`hasPhone`,`hasLinkedIn`,`hasGitHub`,`wordCountOk`,`hasActionVerbs`,`hasSections`,`hasBuzzwords`,`hasQuantifiedImpact`,`hasStandardHeadings`,`hasDateFormats`,`sectionsFound`,`actionVerbCount`,`wordCount`,`suggestions`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ResumeReport entity) {
        statement.bindLong(1, entity.id);
        if (entity.fileName == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.fileName);
        }
        statement.bindLong(3, entity.timestamp);
        statement.bindLong(4, entity.resumeScore);
        statement.bindLong(5, entity.atsScore);
        if (entity.targetRole == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.targetRole);
        }
        final String _tmp = Converters.fromList(entity.matchedKeywords);
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        final String _tmp_1 = Converters.fromList(entity.missingKeywords);
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_1);
        }
        statement.bindDouble(9, entity.keywordMatchRatio);
        final int _tmp_2 = entity.hasEmail ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        final int _tmp_3 = entity.hasPhone ? 1 : 0;
        statement.bindLong(11, _tmp_3);
        final int _tmp_4 = entity.hasLinkedIn ? 1 : 0;
        statement.bindLong(12, _tmp_4);
        final int _tmp_5 = entity.hasGitHub ? 1 : 0;
        statement.bindLong(13, _tmp_5);
        final int _tmp_6 = entity.wordCountOk ? 1 : 0;
        statement.bindLong(14, _tmp_6);
        final int _tmp_7 = entity.hasActionVerbs ? 1 : 0;
        statement.bindLong(15, _tmp_7);
        final int _tmp_8 = entity.hasSections ? 1 : 0;
        statement.bindLong(16, _tmp_8);
        final int _tmp_9 = entity.hasBuzzwords ? 1 : 0;
        statement.bindLong(17, _tmp_9);
        final int _tmp_10 = entity.hasQuantifiedImpact ? 1 : 0;
        statement.bindLong(18, _tmp_10);
        final int _tmp_11 = entity.hasStandardHeadings ? 1 : 0;
        statement.bindLong(19, _tmp_11);
        final int _tmp_12 = entity.hasDateFormats ? 1 : 0;
        statement.bindLong(20, _tmp_12);
        statement.bindLong(21, entity.sectionsFound);
        statement.bindLong(22, entity.actionVerbCount);
        statement.bindLong(23, entity.wordCount);
        final String _tmp_13 = Converters.fromList(entity.suggestions);
        if (_tmp_13 == null) {
          statement.bindNull(24);
        } else {
          statement.bindString(24, _tmp_13);
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM resume_reports WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM resume_reports";
        return _query;
      }
    };
  }

  @Override
  public long insert(final ResumeReport report) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfResumeReport.insertAndReturnId(report);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<ResumeReport> getAll() {
    final String _sql = "SELECT * FROM resume_reports ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfResumeScore = CursorUtil.getColumnIndexOrThrow(_cursor, "resumeScore");
      final int _cursorIndexOfAtsScore = CursorUtil.getColumnIndexOrThrow(_cursor, "atsScore");
      final int _cursorIndexOfTargetRole = CursorUtil.getColumnIndexOrThrow(_cursor, "targetRole");
      final int _cursorIndexOfMatchedKeywords = CursorUtil.getColumnIndexOrThrow(_cursor, "matchedKeywords");
      final int _cursorIndexOfMissingKeywords = CursorUtil.getColumnIndexOrThrow(_cursor, "missingKeywords");
      final int _cursorIndexOfKeywordMatchRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "keywordMatchRatio");
      final int _cursorIndexOfHasEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "hasEmail");
      final int _cursorIndexOfHasPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "hasPhone");
      final int _cursorIndexOfHasLinkedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "hasLinkedIn");
      final int _cursorIndexOfHasGitHub = CursorUtil.getColumnIndexOrThrow(_cursor, "hasGitHub");
      final int _cursorIndexOfWordCountOk = CursorUtil.getColumnIndexOrThrow(_cursor, "wordCountOk");
      final int _cursorIndexOfHasActionVerbs = CursorUtil.getColumnIndexOrThrow(_cursor, "hasActionVerbs");
      final int _cursorIndexOfHasSections = CursorUtil.getColumnIndexOrThrow(_cursor, "hasSections");
      final int _cursorIndexOfHasBuzzwords = CursorUtil.getColumnIndexOrThrow(_cursor, "hasBuzzwords");
      final int _cursorIndexOfHasQuantifiedImpact = CursorUtil.getColumnIndexOrThrow(_cursor, "hasQuantifiedImpact");
      final int _cursorIndexOfHasStandardHeadings = CursorUtil.getColumnIndexOrThrow(_cursor, "hasStandardHeadings");
      final int _cursorIndexOfHasDateFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "hasDateFormats");
      final int _cursorIndexOfSectionsFound = CursorUtil.getColumnIndexOrThrow(_cursor, "sectionsFound");
      final int _cursorIndexOfActionVerbCount = CursorUtil.getColumnIndexOrThrow(_cursor, "actionVerbCount");
      final int _cursorIndexOfWordCount = CursorUtil.getColumnIndexOrThrow(_cursor, "wordCount");
      final int _cursorIndexOfSuggestions = CursorUtil.getColumnIndexOrThrow(_cursor, "suggestions");
      final List<ResumeReport> _result = new ArrayList<ResumeReport>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ResumeReport _item;
        _item = new ResumeReport();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfFileName)) {
          _item.fileName = null;
        } else {
          _item.fileName = _cursor.getString(_cursorIndexOfFileName);
        }
        _item.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _item.resumeScore = _cursor.getInt(_cursorIndexOfResumeScore);
        _item.atsScore = _cursor.getInt(_cursorIndexOfAtsScore);
        if (_cursor.isNull(_cursorIndexOfTargetRole)) {
          _item.targetRole = null;
        } else {
          _item.targetRole = _cursor.getString(_cursorIndexOfTargetRole);
        }
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfMatchedKeywords)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfMatchedKeywords);
        }
        _item.matchedKeywords = Converters.toList(_tmp);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMissingKeywords)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfMissingKeywords);
        }
        _item.missingKeywords = Converters.toList(_tmp_1);
        _item.keywordMatchRatio = _cursor.getFloat(_cursorIndexOfKeywordMatchRatio);
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfHasEmail);
        _item.hasEmail = _tmp_2 != 0;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfHasPhone);
        _item.hasPhone = _tmp_3 != 0;
        final int _tmp_4;
        _tmp_4 = _cursor.getInt(_cursorIndexOfHasLinkedIn);
        _item.hasLinkedIn = _tmp_4 != 0;
        final int _tmp_5;
        _tmp_5 = _cursor.getInt(_cursorIndexOfHasGitHub);
        _item.hasGitHub = _tmp_5 != 0;
        final int _tmp_6;
        _tmp_6 = _cursor.getInt(_cursorIndexOfWordCountOk);
        _item.wordCountOk = _tmp_6 != 0;
        final int _tmp_7;
        _tmp_7 = _cursor.getInt(_cursorIndexOfHasActionVerbs);
        _item.hasActionVerbs = _tmp_7 != 0;
        final int _tmp_8;
        _tmp_8 = _cursor.getInt(_cursorIndexOfHasSections);
        _item.hasSections = _tmp_8 != 0;
        final int _tmp_9;
        _tmp_9 = _cursor.getInt(_cursorIndexOfHasBuzzwords);
        _item.hasBuzzwords = _tmp_9 != 0;
        final int _tmp_10;
        _tmp_10 = _cursor.getInt(_cursorIndexOfHasQuantifiedImpact);
        _item.hasQuantifiedImpact = _tmp_10 != 0;
        final int _tmp_11;
        _tmp_11 = _cursor.getInt(_cursorIndexOfHasStandardHeadings);
        _item.hasStandardHeadings = _tmp_11 != 0;
        final int _tmp_12;
        _tmp_12 = _cursor.getInt(_cursorIndexOfHasDateFormats);
        _item.hasDateFormats = _tmp_12 != 0;
        _item.sectionsFound = _cursor.getInt(_cursorIndexOfSectionsFound);
        _item.actionVerbCount = _cursor.getInt(_cursorIndexOfActionVerbCount);
        _item.wordCount = _cursor.getInt(_cursorIndexOfWordCount);
        final String _tmp_13;
        if (_cursor.isNull(_cursorIndexOfSuggestions)) {
          _tmp_13 = null;
        } else {
          _tmp_13 = _cursor.getString(_cursorIndexOfSuggestions);
        }
        _item.suggestions = Converters.toList(_tmp_13);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ResumeReport getById(final int id) {
    final String _sql = "SELECT * FROM resume_reports WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfResumeScore = CursorUtil.getColumnIndexOrThrow(_cursor, "resumeScore");
      final int _cursorIndexOfAtsScore = CursorUtil.getColumnIndexOrThrow(_cursor, "atsScore");
      final int _cursorIndexOfTargetRole = CursorUtil.getColumnIndexOrThrow(_cursor, "targetRole");
      final int _cursorIndexOfMatchedKeywords = CursorUtil.getColumnIndexOrThrow(_cursor, "matchedKeywords");
      final int _cursorIndexOfMissingKeywords = CursorUtil.getColumnIndexOrThrow(_cursor, "missingKeywords");
      final int _cursorIndexOfKeywordMatchRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "keywordMatchRatio");
      final int _cursorIndexOfHasEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "hasEmail");
      final int _cursorIndexOfHasPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "hasPhone");
      final int _cursorIndexOfHasLinkedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "hasLinkedIn");
      final int _cursorIndexOfHasGitHub = CursorUtil.getColumnIndexOrThrow(_cursor, "hasGitHub");
      final int _cursorIndexOfWordCountOk = CursorUtil.getColumnIndexOrThrow(_cursor, "wordCountOk");
      final int _cursorIndexOfHasActionVerbs = CursorUtil.getColumnIndexOrThrow(_cursor, "hasActionVerbs");
      final int _cursorIndexOfHasSections = CursorUtil.getColumnIndexOrThrow(_cursor, "hasSections");
      final int _cursorIndexOfHasBuzzwords = CursorUtil.getColumnIndexOrThrow(_cursor, "hasBuzzwords");
      final int _cursorIndexOfHasQuantifiedImpact = CursorUtil.getColumnIndexOrThrow(_cursor, "hasQuantifiedImpact");
      final int _cursorIndexOfHasStandardHeadings = CursorUtil.getColumnIndexOrThrow(_cursor, "hasStandardHeadings");
      final int _cursorIndexOfHasDateFormats = CursorUtil.getColumnIndexOrThrow(_cursor, "hasDateFormats");
      final int _cursorIndexOfSectionsFound = CursorUtil.getColumnIndexOrThrow(_cursor, "sectionsFound");
      final int _cursorIndexOfActionVerbCount = CursorUtil.getColumnIndexOrThrow(_cursor, "actionVerbCount");
      final int _cursorIndexOfWordCount = CursorUtil.getColumnIndexOrThrow(_cursor, "wordCount");
      final int _cursorIndexOfSuggestions = CursorUtil.getColumnIndexOrThrow(_cursor, "suggestions");
      final ResumeReport _result;
      if (_cursor.moveToFirst()) {
        _result = new ResumeReport();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfFileName)) {
          _result.fileName = null;
        } else {
          _result.fileName = _cursor.getString(_cursorIndexOfFileName);
        }
        _result.timestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        _result.resumeScore = _cursor.getInt(_cursorIndexOfResumeScore);
        _result.atsScore = _cursor.getInt(_cursorIndexOfAtsScore);
        if (_cursor.isNull(_cursorIndexOfTargetRole)) {
          _result.targetRole = null;
        } else {
          _result.targetRole = _cursor.getString(_cursorIndexOfTargetRole);
        }
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfMatchedKeywords)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfMatchedKeywords);
        }
        _result.matchedKeywords = Converters.toList(_tmp);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfMissingKeywords)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfMissingKeywords);
        }
        _result.missingKeywords = Converters.toList(_tmp_1);
        _result.keywordMatchRatio = _cursor.getFloat(_cursorIndexOfKeywordMatchRatio);
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfHasEmail);
        _result.hasEmail = _tmp_2 != 0;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfHasPhone);
        _result.hasPhone = _tmp_3 != 0;
        final int _tmp_4;
        _tmp_4 = _cursor.getInt(_cursorIndexOfHasLinkedIn);
        _result.hasLinkedIn = _tmp_4 != 0;
        final int _tmp_5;
        _tmp_5 = _cursor.getInt(_cursorIndexOfHasGitHub);
        _result.hasGitHub = _tmp_5 != 0;
        final int _tmp_6;
        _tmp_6 = _cursor.getInt(_cursorIndexOfWordCountOk);
        _result.wordCountOk = _tmp_6 != 0;
        final int _tmp_7;
        _tmp_7 = _cursor.getInt(_cursorIndexOfHasActionVerbs);
        _result.hasActionVerbs = _tmp_7 != 0;
        final int _tmp_8;
        _tmp_8 = _cursor.getInt(_cursorIndexOfHasSections);
        _result.hasSections = _tmp_8 != 0;
        final int _tmp_9;
        _tmp_9 = _cursor.getInt(_cursorIndexOfHasBuzzwords);
        _result.hasBuzzwords = _tmp_9 != 0;
        final int _tmp_10;
        _tmp_10 = _cursor.getInt(_cursorIndexOfHasQuantifiedImpact);
        _result.hasQuantifiedImpact = _tmp_10 != 0;
        final int _tmp_11;
        _tmp_11 = _cursor.getInt(_cursorIndexOfHasStandardHeadings);
        _result.hasStandardHeadings = _tmp_11 != 0;
        final int _tmp_12;
        _tmp_12 = _cursor.getInt(_cursorIndexOfHasDateFormats);
        _result.hasDateFormats = _tmp_12 != 0;
        _result.sectionsFound = _cursor.getInt(_cursorIndexOfSectionsFound);
        _result.actionVerbCount = _cursor.getInt(_cursorIndexOfActionVerbCount);
        _result.wordCount = _cursor.getInt(_cursorIndexOfWordCount);
        final String _tmp_13;
        if (_cursor.isNull(_cursorIndexOfSuggestions)) {
          _tmp_13 = null;
        } else {
          _tmp_13 = _cursor.getString(_cursorIndexOfSuggestions);
        }
        _result.suggestions = Converters.toList(_tmp_13);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
