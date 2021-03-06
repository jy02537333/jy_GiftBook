package pri.zxw.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "database.db";// 数据库名称
	public static final int VERSION = 5;

	public static final String TABLE_CHANNEL = "channel";// 数据表

	public static final String CATEGORYID = "categoryId";//
	public static final String NAME = "name";
	public static final String ORDERID = "orderId";
	public static final String SELECTED = "selected";
	private Context context;

	public SQLHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
	}




	public Context getContext() {
		return context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 创建数据库后，对数据库的操作
		String sql = "create table if not exists " + TABLE_CHANNEL
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORYID
				+ " INTEGER , " + NAME + " TEXT , " + ORDERID + " INTEGER , "
				+ SELECTED + " SELECTED);";
		db.execSQL(sql);
		sql = "create table if not exists " + JsonStrHistoryDao.TABLE
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ JsonStrHistoryDao.Url_key + " TEXT , "
				+ JsonStrHistoryDao.JSON_STR + " TEXT  );";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
