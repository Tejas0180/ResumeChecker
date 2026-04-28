package com.resumechecker.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ReportDao _reportDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `resume_reports` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fileName` TEXT, `timestamp` INTEGER NOT NULL, `resumeScore` INTEGER NOT NULL, `atsScore` INTEGER NOT NULL, `targetRole` TEXT, `matchedKeywords` TEXT, `missingKeywords` TEXT, `keywordMatchRatio` REAL NOT NULL, `hasEmail` INTEGER NOT NULL, `hasPhone` INTEGER NOT NULL, `hasLinkedIn` INTEGER NOT NULL, `hasGitHub` INTEGER NOT NULL, `wordCountOk` INTEGER NOT NULL, `hasActionVerbs` INTEGER NOT NULL, `hasSections` INTEGER NOT NULL, `hasBuzzwords` INTEGER NOT NULL, `hasQuantifiedImpact` INTEGER NOT NULL, `hasStandardHeadings` INTEGER NOT NULL, `hasDateFormats` INTEGER NOT NULL, `sectionsFound` INTEGER NOT NULL, `actionVerbCount` INTEGER NOT NULL, `wordCount` INTEGER NOT NULL, `suggestions` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '47abae61014fc31b1dedc6aca8144e90')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `resume_reports`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsResumeReports = new HashMap<String, TableInfo.Column>(24);
        _columnsResumeReports.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("fileName", new TableInfo.Column("fileName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("resumeScore", new TableInfo.Column("resumeScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("atsScore", new TableInfo.Column("atsScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("targetRole", new TableInfo.Column("targetRole", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("matchedKeywords", new TableInfo.Column("matchedKeywords", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("missingKeywords", new TableInfo.Column("missingKeywords", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("keywordMatchRatio", new TableInfo.Column("keywordMatchRatio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasEmail", new TableInfo.Column("hasEmail", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasPhone", new TableInfo.Column("hasPhone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasLinkedIn", new TableInfo.Column("hasLinkedIn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasGitHub", new TableInfo.Column("hasGitHub", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("wordCountOk", new TableInfo.Column("wordCountOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasActionVerbs", new TableInfo.Column("hasActionVerbs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasSections", new TableInfo.Column("hasSections", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasBuzzwords", new TableInfo.Column("hasBuzzwords", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasQuantifiedImpact", new TableInfo.Column("hasQuantifiedImpact", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasStandardHeadings", new TableInfo.Column("hasStandardHeadings", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("hasDateFormats", new TableInfo.Column("hasDateFormats", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("sectionsFound", new TableInfo.Column("sectionsFound", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("actionVerbCount", new TableInfo.Column("actionVerbCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("wordCount", new TableInfo.Column("wordCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResumeReports.put("suggestions", new TableInfo.Column("suggestions", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysResumeReports = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesResumeReports = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoResumeReports = new TableInfo("resume_reports", _columnsResumeReports, _foreignKeysResumeReports, _indicesResumeReports);
        final TableInfo _existingResumeReports = TableInfo.read(db, "resume_reports");
        if (!_infoResumeReports.equals(_existingResumeReports)) {
          return new RoomOpenHelper.ValidationResult(false, "resume_reports(com.resumechecker.model.ResumeReport).\n"
                  + " Expected:\n" + _infoResumeReports + "\n"
                  + " Found:\n" + _existingResumeReports);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "47abae61014fc31b1dedc6aca8144e90", "3931aa2e45b8c2d0af43278a488ef80f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "resume_reports");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `resume_reports`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ReportDao.class, ReportDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ReportDao reportDao() {
    if (_reportDao != null) {
      return _reportDao;
    } else {
      synchronized(this) {
        if(_reportDao == null) {
          _reportDao = new ReportDao_Impl(this);
        }
        return _reportDao;
      }
    }
  }
}
