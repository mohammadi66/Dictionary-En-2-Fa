package ir.xzn.internetwan.boshlambo;

import android.os.Bundle;

import android.app.ListActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private database db;
	private EditText ed_txt;
	private TextView tv;
	private RadioButton rb_en;
	private RadioButton rb_fa;
	
	private String[] searched_word;
	private String[] en;
	private String[] fa;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db=new database(this, "dictionary", null, 1);
		db.useable();
		
		ed_txt=(EditText)findViewById(R.id.ed_txt);
		tv=(TextView)findViewById(R.id.tv);
		rb_en=(RadioButton)findViewById(R.id.rb_en);
		rb_fa=(RadioButton)findViewById(R.id.rb_fa);
		
		//refresher(ed_txt.getText().toString(), "en");
		
		ed_txt.addTextChangedListener(new TextWatcher() {
		
		
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if(rb_en.isChecked()){
					refresher(ed_txt.getText().toString(), "en");

				}
				else if (rb_fa.isChecked()) {
					refresher(ed_txt.getText().toString(), "per");

				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	}

	class AA extends ArrayAdapter<String>{
		
		public AA(){
			super(MainActivity.this,R.layout.row_search,en);
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater in=getLayoutInflater();
			View row=in.inflate(R.layout.row_search, parent, false);
			
			
			TextView tv_searched_word=(TextView)row.findViewById(R.id.tv_searched_word);
			
			tv_searched_word.setText(en[position]);
		
			return (row);
		}
		
	}
	
	
	private void refresher(String text, String field){
		db.open();
		int s = db.shmaresh_jostojoo(text, field);
		if (ed_txt.getText().toString().equals("")) {
			s = 0;
			tv.setText(" لطفا کلمه مورد نطرتان را وارد کنید");
		}else {
			tv.setText(" تعداد "+s+" یافت شد ");

		}
		//searched_word[s];
		en=new String[s];
		fa=new String[s];
		
		for (int i = 0; i < s; i++) {
			//searched_word[i]=db.jostojoo(i, col, word, field);
			if(field.equals("en")){
				en[i]=db.jostojoo(i, 1, text, field);
			}else{
				en[i]=db.jostojoo(i, 2, text, field);
			}
			//en[i]=db.jostojoo(i, 1, text, field);
			//fa[i]=db.jostojoo(i, 2, text, field);
		}
		
		
		
		setListAdapter(new AA());
		db.close();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
