package org.no_ip.dauerfeuer.skatmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "SkatManager";

    protected boolean isOnline;

    private DataLoader mDataLoader;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataLoader.registerNetworkCallback(this);

        mDataLoader = new DataLoader(this);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataLoader.refreshMainActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDataLoader.refreshMainActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.main_activity_menu_prefs:
                break;
            case R.id.main_activity_menu_refresh:
                mSwipeRefreshLayout.setRefreshing(true);
                mDataLoader.refreshMainActivity();
                break;
            default:
                break;
        }

        if(item.getItemId() == R.id.main_activity_menu_prefs) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /*public class LoadGames extends AsyncTask<Context, Void, List<Game>> {
        @Override
        protected List<Game> doInBackground(Context... c) {
            List<Game> gameList = Game.readGames(c[0]);
            if(gameList == null || gameList.size() < 1) runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.tv_no_games).setVisibility(View.VISIBLE);
                }
            });
            else runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.tv_no_games).setVisibility(View.GONE);
                }
            });
            return gameList;
        }

        @Override
        protected void onPostExecute(List<Game> games) {
            mAdapter = new MyAdapter(games, MainActivity.this);
            ((MyAdapter) mAdapter).setDeleteClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(final Game item) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.delete_game);
                    builder.setMessage(R.string.confirm_delete_game);
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            item.removeGame(MainActivity.this);
                            new LoadGames().execute(MainActivity.this);
                        }
                    });
                    builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
            ((MyAdapter) mAdapter).setStartClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(Game item) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("game", item);
                    MainActivity.this.startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        }
    }*/
}
