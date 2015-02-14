package com.voca.vocable.db;

import java.io.InputStream;
import java.sql.SQLException;

import jxl.Sheet;
import jxl.Workbook;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class VocaDatabaseAdapter {



	public static final String TABLE_NAME = "vocable";
	/**
	 * index
	 */
	public static final String C_NAME1 = "voca_index";
	/**
	 * word
	 */
	public static final String C_NAME2 = "word";
	/**
	 * meaning
	 */
	public static final String C_NAME3 = "meaning";
	/**
	 * save
	 */
	public static final String C_NAME4 = "save";
	/**
	 * gubun
	 */
	public static final String C_NAME5 = "gubun";
	/**
	 * synonym
	 */
	public static final String C_NAME6 = "synonym";

	/**
	 * days
	 */
	public static final String C_NAME7 = "days";

	private static final String DATABASE_NAME = "voca.db";
	private static final int DATABASE_VERSION = 1;
	private VocaDatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private Context mContext;


	public static boolean CREATE_YN = false;

	public VocaDatabaseAdapter(Context ctx) {
		this.mContext = ctx;
	}

	public VocaDatabaseAdapter open() throws SQLException {
		mDbHelper = new VocaDatabaseHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}


	/**
	 * 단어, 뜻, 저장여부(y/n), 구분(1f,2i,3c), 동의어 
	 * @param c2
	 * @param c3
	 * @param c4
	 * @param c5
	 * @param c6
	 * @return
	 */
	public long createNote(String c2, String c3, String c4, String c5, String c6, String c7, SQLiteDatabase db) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(C_NAME2, c2);
		initialValues.put(C_NAME3, c3);
		initialValues.put(C_NAME4, c4);
		initialValues.put(C_NAME5, c5);
		initialValues.put(C_NAME6, c6);
		initialValues.put(C_NAME7, c7);
		return db.insert(TABLE_NAME, null, initialValues);
	}



	class VocaDatabaseHelper extends SQLiteOpenHelper{



		public VocaDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		public VocaDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(final SQLiteDatabase db) {
			String sql = "create table " + TABLE_NAME +"("
					+ C_NAME1+" integer primary key autoincrement, " 
					+ C_NAME2+" text,"
					+ C_NAME3+" text,"
					+ C_NAME4+" text,"
					+ C_NAME5+" text,"
					+ C_NAME6+" text,"
					+ C_NAME7+" text);";
			db.execSQL(sql);
			CREATE_YN = true;
			insertFaundation("F-DAY1.xls",db, "1");
			insertFaundation("F-DAY2.xls",db, "2");
			insertFaundation("F-DAY3.xls",db, "3");
			insertFaundation("F-DAY4.xls",db, "4");
			insertFaundation("F-DAY5.xls",db, "5");
			insertFaundation("F-DAY6.xls",db, "6");
			insertFaundation("F-DAY7.xls",db, "7");
			insertFaundation("F-DAY8.xls",db, "8");
			insertFaundation("F-DAY9.xls",db, "9");
			insertFaundation("F-DAY10.xls",db, "10");
			insertFaundation("F-DAY11.xls",db, "11");
			insertFaundation("F-DAY12.xls",db, "12");
			insertFaundation("F-DAY13.xls",db, "13");
			insertFaundation("F-DAY14.xls",db, "14");
			insertFaundation("F-DAY15.xls",db, "15");
			insertFaundation("F-DAY16.xls",db, "16");
			insertFaundation("F-DAY17.xls",db, "17");
			insertFaundation("F-DAY18.xls",db, "18");
			insertFaundation("F-DAY19.xls",db, "19");
			insertFaundation("F-DAY20.xls",db, "20");

			insertCompletion("C-DAY1.xls", db, "1");
			insertCompletion("C-DAY2.xls", db, "2");
			insertCompletion("C-DAY3.xls", db, "3");
			insertCompletion("C-DAY4.xls", db, "4");
			insertCompletion("C-DAY5.xls", db, "5");
			insertCompletion("C-DAY6.xls", db, "6");
			insertCompletion("C-DAY7.xls", db, "7");
			insertCompletion("C-DAY8.xls", db, "8");
			insertCompletion("C-DAY9.xls", db, "9");
			insertCompletion("C-DAY10.xls", db, "10");
			insertCompletion("C-DAY11.xls", db, "11");
			insertCompletion("C-DAY12.xls", db, "12");
			insertCompletion("C-DAY13.xls", db, "13");
			insertCompletion("C-DAY14.xls", db, "14");
			insertCompletion("C-DAY15.xls", db, "15");
			insertCompletion("C-DAY16.xls", db, "16");
			insertCompletion("C-DAY17.xls", db, "17");
			insertCompletion("C-DAY18.xls", db, "18");
			insertCompletion("C-DAY19.xls", db, "19");
			insertCompletion("C-DAY20.xls", db, "20");

			insertIntroduction("I-DAY 1.xls", db, "1");
			insertIntroduction("I-DAY 2.xls", db, "2");
			insertIntroduction("I-DAY 3.xls", db, "3");
			insertIntroduction("I-DAY 4.xls", db, "4");
			insertIntroduction("I-DAY 5.xls", db, "5");
			insertIntroduction("I-DAY 6.xls", db, "6");
			insertIntroduction("I-DAY 7.xls", db, "7");
			insertIntroduction("I-DAY 8.xls", db, "8");
			insertIntroduction("I-DAY 9.xls", db, "9");
			insertIntroduction("I-DAY 10.xls", db, "10");
			insertIntroduction("I-DAY 11.xls", db, "11");
			insertIntroduction("I-DAY 12.xls", db, "12");
			insertIntroduction("I-DAY 13.xls", db, "13");
			insertIntroduction("I-DAY 14.xls", db, "14");
			insertIntroduction("I-DAY 15.xls", db, "15");
			insertIntroduction("I-DAY 16.xls", db, "16");
			insertIntroduction("I-DAY 17.xls", db, "17");
			insertIntroduction("I-DAY 18.xls", db, "18");
			insertIntroduction("I-DAY 19.xls", db, "19");
			insertIntroduction("I-DAY 20.xls", db, "20");
		}

		//		/**
		//		 * 파운데이션 단어 인설트
		//		 * @param day
		//		 * @param db
		//		 */
		//		private void insertFaundation(String[] day, SQLiteDatabase db) {
		//			for(int i = 0 ; i < day.length; i ++) {
		//				String[] word = day[i].split(VocaDatabaseSQL.TAB_GUBUN);
		//				word[1] = word[1].replace(VocaDatabaseSQL.WORD_GUBUN, "");
		//				createNote(word[0], word[1], "n", "1", "", "1", db);
		//			}
		//		}

		/**
		 * 파운데이션 단어 인설트
		 * @param fileName
		 * @param db
		 * @param days
		 */
		private void insertFaundation(String fileName, SQLiteDatabase db, String days) {

			Workbook workbook = null;
			Sheet sheet = null;

			try {
				InputStream is = mContext.getResources().getAssets().open(fileName);
				workbook = Workbook.getWorkbook(is);

				if (workbook != null) {
					sheet = workbook.getSheet(0);

					if (sheet != null) {

						int nMaxColumn = 2;
						int nRowStartIndex = 0;
						int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
						int nColumnStartIndex = 0;
						int nColumnEndIndex = sheet.getRow(2).length - 1;

						for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
							String word = sheet.getCell(nColumnStartIndex, nRow).getContents();
							String meaning = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
							createNote(word, meaning, "n", "1", "", days, db);
						}
					} else {
					}
				} else {
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (workbook != null) 
					workbook.close();
			}
		}

		/**
		 * 인트로덕션 인설트
		 * @param fileName
		 * @param db
		 * @param days
		 */
		private void insertIntroduction(String fileName, SQLiteDatabase db, String days) {

			Workbook workbook = null;
			Sheet sheet = null;

			try {
				InputStream is = mContext.getResources().getAssets().open(fileName);
				workbook = Workbook.getWorkbook(is);

				if (workbook != null) {
					sheet = workbook.getSheet(0);

					if (sheet != null) {

						int nMaxColumn = 3;
						int nRowStartIndex = 0;
						int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
						int nColumnStartIndex = 0;
						int nColumnEndIndex = sheet.getRow(2).length - 1;

						for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
							String word = sheet.getCell(nColumnStartIndex, nRow).getContents();
							String meaning = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
							String synonym = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();
							createNote(word, meaning, "n", "2", synonym, days, db);
						}
					} else {
					}
				} else {
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (workbook != null) 
					workbook.close();
			}
		}

		/**
		 * 컴플리션 인설트
		 * @param fileName
		 * @param db
		 * @param days
		 */
		private void insertCompletion(String fileName, SQLiteDatabase db, String days) {

			Workbook workbook = null;
			Sheet sheet = null;

			try {
				InputStream is = mContext.getResources().getAssets().open(fileName);
				workbook = Workbook.getWorkbook(is);

				if (workbook != null) {
					sheet = workbook.getSheet(0);

					if (sheet != null) {

						int nMaxColumn = 3;
						int nRowStartIndex = 0;
						int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
						int nColumnStartIndex = 0;
						int nColumnEndIndex = sheet.getRow(2).length - 1;

						for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
							String word = sheet.getCell(nColumnStartIndex, nRow).getContents();
							String meaning = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
							String synonym = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();
							createNote(word, meaning, "n", "3", synonym, days, db);
						}
					} else {
					}
				} else {
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (workbook != null) 
					workbook.close();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			String sql = "drop table if exists "+TABLE_NAME;

			db.execSQL(sql);

			onCreate(db);
		}

	}


	/**
	 * 전체 데이터 가져오기
	 * @return
	 */
	public Cursor fetchAllNotes() {
		return mDb.query(TABLE_NAME, new String[] { C_NAME1, C_NAME2, C_NAME3, C_NAME4, C_NAME5, C_NAME6, C_NAME7 }, null, null, null, null, null);
	}

	/**
	 * 구분자와 날짜별로 단어장 가져오기
	 * @param gubun
	 * @param days
	 * @return
	 * @throws SQLException
	 */
	public Cursor fetchNote(String gubun, String days) throws SQLException {

		Cursor mCursor = mDb.query(true, TABLE_NAME, new String[] { C_NAME1, C_NAME2, C_NAME3, C_NAME4, C_NAME5, C_NAME6, C_NAME7 },
				C_NAME5 +"="+ gubun +" AND "+C_NAME7 +"="+days, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/**
	 * my vocab 가져오기
	 * @param gubun
	 * @param days
	 * @return
	 * @throws SQLException
	 */
	public Cursor fetchSaveNote(String gubun, String days) throws SQLException {

		String save = "y";
		Cursor mCursor = mDb.query(true, TABLE_NAME, new String[] { C_NAME1, C_NAME2, C_NAME3, C_NAME4, C_NAME5, C_NAME6, C_NAME7 },
				C_NAME4 + "=" + save, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/**
	 * 구분자로 단어장 가져오기
	 * @param gubun
	 * @return
	 * @throws SQLException
	 */
	public Cursor fetchNote(String gubun) throws SQLException {

		Cursor mCursor = mDb.query(true, TABLE_NAME, new String[] { C_NAME1, C_NAME2, C_NAME3, C_NAME4, C_NAME5, C_NAME6, C_NAME7 },
				C_NAME5 +"="+ gubun, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/**
	 *  단어장 검색
	 * @param search
	 * @return
	 * @throws SQLException
	 */
	public Cursor searchNote(String search) throws SQLException {

		Cursor mCursor = mDb.query(true, TABLE_NAME, new String[] { C_NAME1, C_NAME2, C_NAME3, C_NAME4, C_NAME5, C_NAME6, C_NAME7 },
				C_NAME2 +" like ?", new String[] { search + "%" }, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	/**
	 * 이미 저장한 단언지 확인
	 * @param index
	 * @return
	 */
	public boolean checkNote(int index) {
		Cursor cursor = mDb.query(true, TABLE_NAME, new String[] {C_NAME4} ,
				C_NAME1 +"="+ index, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		String saveYn = "n";
		while(!cursor.isAfterLast()) {
			saveYn = cursor.getString(cursor.getColumnIndex(VocaDatabaseAdapter.C_NAME4)); 
			cursor.moveToNext();
		}
		cursor.close();
		return saveYn.equals("y") ? true : false;
	}

	/**
	 * 단어 저장여부 업데이트
	 * @param saveYn
	 * @param index
	 * @return
	 */
	public boolean updateNote(String saveYn, int index) {
		ContentValues args = new ContentValues();
		args.put(C_NAME4, saveYn);
		return mDb.update(TABLE_NAME, args, C_NAME1 + "=" + index, null) > 0;
	}
}
