package com.example.administrator.databindingdemo;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.databindingdemo.databinding.ActivityMainBinding;

//
public class MainActivity extends AppCompatActivity {
    Student mStudent = new Student("guo","xiaopang");
    private ActivityMainBinding mBinding;
    Boolean visibility = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //binding.textView2.setText(mStudent.toString());

        //绑定变量
        mBinding.setStudent(mStudent);
        mBinding.setPresenter(new Presenter());
        mBinding.setVisibility(visibility);
        //binding.setVariable(BR.student,mStudent);
        //viewstub inflate;
        View inflate = mBinding.viewStub.getViewStub().inflate();
    }

    //事件绑定 方法类
    public class Presenter{
        public void onTextChanged(CharSequence s, int start, int before, int count){
            mStudent.setFirstName(s.toString());
            //如果已经为该类继承了BaseObservable 就不需要再赋值了；
            //mBinding.setStudent(mStudent);
        }

        public void onClick(View view){
            Toast.makeText(MainActivity.this,"onClick!",Toast.LENGTH_SHORT).show();
            visibility = !visibility;
            mBinding.setVisibility(visibility);
            //mBinding.viewStub.getBinding().setVariable(BR.visibility,!visibility);
        }

        public void onClickListenerBinding(Student student){
            Toast.makeText(MainActivity.this, student.getLastName(),Toast.LENGTH_SHORT).show();
        }
    }

}
