package com.example.hirorock1103.template_01;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Anken.AnkenType;
import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.DB.AnkenTypeManager;

import java.util.List;

public class MainAnkenTypeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<AnkenType> list;
    private AnkenTypeAdapter adapter;
    private AnkenTypeManager ankenTypeManager;

    //for add ankenType
    private EditText editAnkenType;
    private Button btAdd;

    private int ankenTypeId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_anken_type_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //view
        editAnkenType = findViewById(R.id.edit_title);
        btAdd = findViewById(R.id.add);

        setListener();

        // get anken type
        ankenTypeManager = new AnkenTypeManager(this);
        list = ankenTypeManager.getList();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        adapter = new AnkenTypeAdapter(list);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private void reloadView(){
        list = ankenTypeManager.getList();
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    private void setListener(){
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add ankenType
                AnkenType ankenType = new AnkenType();
                ankenType.setTypeName(editAnkenType.getText().toString());
                ankenType.setStatus(0);

                String error = "";
                //check
                if(ankenType.getTypeName().isEmpty()){
                    error += "名称は必須です。";
                }else{
                    if(ankenTypeManager.isDuplicate(ankenType.getTypeName())){
                        error += "名称が重複しています。";
                    }
                }

                if(error.isEmpty()){
                    long insertId = ankenTypeManager.addType(ankenType);

                    if(insertId > 0){
                        Snackbar.make(v,"登録完了", Snackbar.LENGTH_SHORT).show();
                        reloadView();
                    }
                }else{

                    Snackbar.make(v,error,Snackbar.LENGTH_LONG).show();

                }



            }
        });
    }

    public class AnkenTypeViewHolder extends RecyclerView.ViewHolder{

        private TextView ankenTypeName;
        private ConstraintLayout layout;

        public AnkenTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            ankenTypeName = itemView.findViewById(R.id.anken_type_title);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    public class AnkenTypeAdapter extends RecyclerView.Adapter<AnkenTypeViewHolder>{

        private List<AnkenType> list;

        public AnkenTypeAdapter(List<AnkenType> list){
            this.list = list;
        }

        public void setList(List<AnkenType> list){
            this.list = list;
        }

        @NonNull
        @Override
        public AnkenTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(MainAnkenTypeListActivity.this).inflate(R.layout.item_row2, null);
            AnkenTypeViewHolder holder = new AnkenTypeViewHolder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull AnkenTypeViewHolder holder, int i) {

            holder.ankenTypeName.setText(list.get(i).getTypeName());
            holder.layout.setTag(list.get(i).getId());
            registerForContextMenu(holder.layout);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ankenTypeId = Integer.parseInt(v.getTag().toString());
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_1, menu);

        Common.log("ankenTypeId:" + ankenTypeId);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.option1:

                break;

            case R.id.option2:
                //削除
                ankenTypeManager.delete(ankenTypeId);
                View view = findViewById(android.R.id.content);
                Snackbar.make(view, "削除しました。", Snackbar.LENGTH_SHORT).show();
                reloadView();
                break;


        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
