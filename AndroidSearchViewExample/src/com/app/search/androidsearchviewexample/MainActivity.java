package com.app.search.androidsearchviewexample;
 
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.SearchView;
import android.widget.TextView;
 
public class MainActivity extends Activity {
 
    private TextView displayText;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        displayText = (TextView)findViewById(R.id.searchViewResult);
         
        initSearchView();
    }
 
    private void initSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
    }
 
    @Override
    protected void onNewIntent(Intent intent) {
        if (ContactsContract.Intents.SEARCH_SUGGESTION_CLICKED.equals(intent.getAction())) {
            String displayName = getContactName(intent);
            displayText.setText(displayName);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            displayText.setText("search for: '" + query + "'...");
        }
    }
 
    private String getContactName(Intent intent) {
        Cursor phoneCursor = getContentResolver().query(intent.getData(), null, null, null, null);
        phoneCursor.moveToFirst();
        int colNameIndex = phoneCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String contactName = phoneCursor.getString(colNameIndex);
        phoneCursor.close();
        return contactName;
    }
 
}