package com.example.administrator.databindingdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.databindingdemo.databinding.ActivityMainBinding;

//
public class MainActivity extends AppCompatActivity {
    Student mStudent = new Student("guo","chengqian");
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //binding.textView2.setText(mStudent.toString());

        //绑定变量
        mBinding.setStudent(mStudent);
        mBinding.setPresenter(new Presenter());
        //binding.setVariable(BR.student,mStudent);
    }

    //事件绑定 方法类
    public class Presenter{
        public void onTextChanged(CharSequence s, int start, int before, int count){
            mStudent.setFirstName(s.toString());
            mBinding.setStudent(mStudent);
        }

        public void onClick(View view){
            Toast.makeText(MainActivity.this,"onClick!",Toast.LENGTH_SHORT).show();
        }

        public void onClickListenerBinding(Student student){
            Toast.makeText(MainActivity.this, student.getLastName(),Toast.LENGTH_SHORT).show();
        }
    }

}
