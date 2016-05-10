package com.tiqiuzhe.touxiangupdate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.bean.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/5/10.
 */
public class MoreCheckBoxActivity extends AppCompatActivity {

    @Bind(R.id.rec_person)
    RecyclerView mRecPerson;
    List<Person> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select);
        ButterKnife.bind(this);
        personList=new ArrayList<>();
        for (int i=0;i<10;i++){
            Person person=new Person("人物"+i,i,R.mipmap.ic_launcher);
            personList.add(person);
        }

        mRecPerson.setHasFixedSize(true);
        mRecPerson.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mRecPerson.setAdapter(new PersonAdapter(personList));

    }

    public class PersonAdapter extends RecyclerView.Adapter{

        private List<Person> data;
        public PersonAdapter(List<Person> persons){
            this.data=persons;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof PersonHolder){
                PersonHolder personHolder=(PersonHolder)holder;
                Person person=data.get(position);
                personHolder.mIvSelect.setVisibility(person.isChecked()? View.VISIBLE:View.INVISIBLE);
                personHolder.mTvName.setText(person.getName());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class PersonHolder extends RecyclerView.ViewHolder{

            @Bind(R.id.iv_person)
            ImageView mIvPerson;
            @Bind(R.id.tv_name)
            TextView mTvName;
            @Bind(R.id.iv_select)
            ImageView mIvSelect;
            public PersonHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Person person=data.get(getAdapterPosition());
                        person.setChecked(!person.isChecked());
                        notifyItemChanged(getAdapterPosition());

                        StringBuffer buffer=new StringBuffer();
                        for (Person p:data){
                            if (p.isChecked()){
                                buffer.append(p.getName());
                                buffer.append(",");
                            }
                        }
                        Toast.makeText(getApplication(), "选中了:" + buffer, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
