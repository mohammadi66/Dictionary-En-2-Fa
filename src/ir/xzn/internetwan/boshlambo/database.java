package ir.xzn.internetwan.boshlambo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
	
	 public final String   path = "data/data/ir.xzn.internetwan.boshlambo/databases/";
	    public final String   Name = "dictionary";
	    public SQLiteDatabase mydb;
	    private final Context mycontext;


	public database(Context context, String Name, CursorFactory factory,
			int version) {
		super(context, "dictionary", null, 1);
		mycontext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public void useable() {
        boolean checkdb = checkdb();
        if (checkdb) {

        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            }
            catch (IOException e) {	
            
            }
        }
    }


    public void open() {
        mydb = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READWRITE);
    }


    @Override
    public void close() {
        mydb.close();
    }


    public boolean checkdb() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLException e) {

        }
        return db != null ? true : false;
    }


    public void copydatabase() throws IOException {

        OutputStream myOutput = new FileOutputStream(path + Name);
        byte[] buffer = new byte[1024];
        int lenght;
        InputStream myInput = mycontext.getAssets().open(Name);
        while ((lenght = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, lenght);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }
	
    public String jostojoo(int row , int col , String word , String field){
    	Cursor cursor;
    	if (field.equals("en")) {
    		cursor = mydb.rawQuery("select * from dic where "+field+" like '%"+word+"%' group by en", null);
		}else {
    		cursor = mydb.rawQuery("select * from dic where "+field+" like '%"+word+"%'", null);
		}
    	cursor.moveToPosition(row);
    	String save = cursor.getString(col);	
    	return save;
    }
          
    
    public Integer shmaresh_jostojoo(String word , String field){
    	Cursor cursor;
    	if (field.equals("en")) {
    		cursor = mydb.rawQuery("select * from dic where "+field+" like '%"+word+"%' group by en", null);
		}else {
    		cursor = mydb.rawQuery("select * from dic where "+field+" like '%"+word+"%'", null);
		}
    	int save = cursor.getCount();
    	return save;
    }
    
    
    
     
}
