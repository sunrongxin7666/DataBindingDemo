>2015年的Google IO大会上，Android 团队发布了一个数据绑定框架（*Data Binding Library*），官方原生支持 **MVVM** 模型。数据绑定的概念并不陌生，Web开发中已经很是普遍，因此*DataBinding*或多或少地都借鉴了Web端开成熟的经验，其语法与使用方式都和JSP中的EL表达式非常类似。经过不断地强化，到了2016年，*DataBinding*已经可以支持数据双向绑定。
 
##1. DataBinding简介
**DataBinding**解决了Android UI 编程的一个痛点，其主要优势在于：
1. 对于MVVM的支持：官方原生支持 MVVM 模型可以让我们在不改变既有代码框架的前提下，非常容易地使用这些新特性。
2. 提高开发效率：
	- 去掉Acitivity和Fragments中更新UI数据的代码，让业务逻辑和UI代码分离；
	- XML成为UI数据的唯一真实来源；
	- 减少定义view id 和使用findViewById();
3. 性能高、功能强：
	- 充分考虑了性能因素，高效的绑定和更新数据；
	- 更安全，在编译时会发现由于错误的ID而引起的Errors；
	- 保证代码在主线程经常；

在DataBinding推出之前，市面上已经有类似的第三方库在被使用，比如：
- ButterKnife;
- Android Annotations;
- RoboBinding;

DataBinding让这些依赖注入框架会慢慢地失去市场，因为在 Java代码中直接使用View变量的情况会越来越少。

但是DataBinding毕竟还在发展阶段，有些地方还值得完善：
- IDE的支持不足善：虽然DataBinding是Google亲生的，但是Android Studio中的支持还不是不尽如人意，尤其是在XML文件中代码提示有待加强；
- 报错信息不直接：这个是老生常谈的问题，虽然DataBinding在编译时能发现数据绑定的错误，但是报错信息往往不直观，需要用户在很多错误信息中自己去找错误点。
- 没有重构的支持：这可以说是所有数据绑定框架都会面临的问题，因为数据的绑定都是在XML中，一旦用户需要继承原来的代码，就压面临这将所有数据绑定代码重新编写。

##2. DataBinding的基本使用
### 准备工作
1. 确保Android stuido的版本一定要大于1.3。
2. Android的Gradle插件版本不低于 1.5.0-alpha1：
```
classpath 'com.android.tools.build:gradle:1.5.0'
```
2. 然后修改对应模块（Module）的 build.grade：
```
android {
    ....
    dataBinding {
        enabled = true
    }
}
```
工程创建完成后，我们通过一个最简单的例子来说明 Data Binding 的基本用法。

### 布局文件

使用DataBinding之后，xml的布局文件就不再用于单纯地展示UI元素，还需要定义UI元素用到的变量。所以，它的根节点不再是一个 ViewGroup，而是变成了**layout**，并且新增了一个节点**data**，用来定义绑定的数据。

```
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
    <!--添加要绑定的数据-->
    </data>
    
    <!--下面的布局文件就是原先的根节点（Root Element）-->
    <LinearLayout>
    ....
    </LinearLayout>
</layout>
```
**data**节点的作用就像一个桥梁，Activity中将data需要数据传入，XML定义data数据绑定的位置，搭建了 View 和 Model 之间的通路，也就是实现MVVM的*ViewModel*。

下面说明下如何在XML定义data节点：我们先在 xml 布局文件的data节点中声明**variable**节点，这个节点将会定义变量的名称、类型，为UI元素提供数据（例如将String绑定到TextView的android:text属性上）。
 
```
 <data>
        <!--java.lang.*包是自动导入的不需要你再次的导入-->
        <!--当出现了import相同的类名的时候需要为其指定外号alias加以区分-->
        <import type="com.example.administrator.databindingdemo.Student"/>
        <import type="com.example.administrator.databindingdemo.MainActivity.Presenter"/>
        <variable
            name="student"
            type="Student"/>
        <variable
            name="presenter"
            type="Presenter"/>
        <variable
            name="visibility"
            type="Boolean"/>
</data>
```
其中Presenter类时用来绑定方法的类，我们稍后再说；Student类是我们定义的一个类，用来呈现数据，其定义如下：

```
public class Student extends BaseObservable{
    private String firstName;
    private String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        //提示该属性刷新了
        notifyPropertyChanged(BR.firstName);
        //提示所有的属性都刷新
        //notifyAll();
    }

    //注解来提示Binding生成这个字段
    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                       "firstName='" + firstName + '\'' +
                       ", lastName='" + lastName + '}';
    }
}
```
Studnet类继承了**BaseObservable**，这个是数据更新时所需要的，我们稍后在再来讨论。

当我们设置DataBinding生效后，如果一个xml文件的被如上这般设置了Data节点，编译器就会自动生成一个继承自 ViewDataBinding 的类（build 目录下）。其命名规则也是固定的：如果 xml 的文件名叫 activity_main.xml，那么生成的类就是 ActivityMainBinding。

### 绑定数据变量

下面需要修改MainActivity的onCreate方法，用 DatabindingUtil.setContentView()来替换掉setContentView()，然后创建一个Student对象，通过mBinding.setStudent(mStudent)与 variable 进行绑定。

```
Student mStudent = new Student("guo","chengqian");
private ActivityMainBinding mBinding;
Boolean visiblity = true;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        
    //绑定变量
    mBinding.setStudent(mStudent);
    mBinding.setPresenter(new Presenter());
    mBinding.setVisibility(visiblity);
}
```

ActivityMainBinding类中所有的set方法是根据variable名称生成的。我们在XML文件中定义了三个变量，所以会生成三个set方法。

### 在XML中使用变量
当数据传入ViewDataBinding与Variable绑定之后，xml的UI元素就可以直接使用了。
```
<TextView
            android:id="@+id/textView1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{student.firstName}"
            />
            
<TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:visibility="@{visibility?View.VISIBLE:View.GONE}"
            android:text="@{student.lastName}"
/>

```
直接在对应属性上**@{variable}**就可以使用该变量的值以及对应的属性。

现在运行程序，就会看到我们传入Student的姓名已经显示出来了

![1500001896236.png](http://upload-images.jianshu.io/upload_images/3297585-b36b998d76422452.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##3. 事件方法绑定
仅仅是在UI上绑定数据还远远不够，UI上的响应事件也需要被处理，因此需要要把响应事件的方法绑定到View上。

事件方法的绑定有两种方法：第一种是方法引用，这种方法要求函数的参数个数，类型以及顺序都会原生的事件方法相同；第二种方法是使用*lambda*表达式，这种方法更为灵活，可以自定义参数。

我们来看下例子：
这是一个方法类的实现，之前在xml的data节点中已经声明了变量，在Activity中也进行了绑定。
```
//方法类
public class Presenter{
	//方法引用 参数要求符合接口定义
    public void onTextChanged(CharSequence s, int start, int before, int count){
        mStudent.setFirstName(s.toString());
        //如果已经为该类继承了BaseObservable 就不需要再赋值了；
        //mBinding.setStudent(mStudent);
    }
	
	//方法引用，参数要求符合接口定义；
    public void onClick(View view){
        Toast.makeText(MainActivity.this,"onClick!",Toast.LENGTH_SHORT).show();
    }
	
	//自定义方法
    public void onClickListenerBinding(Student student){
        Toast.makeText(MainActivity.this, student.getLastName(),Toast.LENGTH_SHORT).show();
    }
}
```
首先在EidtText控件中使用，将onTextChanged方法绑定。
```
<EditText
    android:id="@+id/first_name_ed_txt"
    android:onTextChanged="@{presenter.onTextChanged}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:hint="First Name"/>
```
这样一旦输入的内容改变，变量student中对应的firstName属性也会跟着改变。

同样，我们将onClick方法绑定到一个TextView上
```
<TextView
	android:id="@+id/textView1"
    android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{student.firstName}"
            android:onClick="@{presenter.onClick}"
            />
```
但是这种方法不够灵活，绑定的方法必须和View所要求的接口参数一致。为了更为灵活的绑定方法，还可以使用**Lambda**表达式。

```
   <TextView
        android:id="@+id/textView2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:visibility="@{visibility?View.VISIBLE:View.GONE}"
        android:text="@{student.lastName}"
        android:onClick="@{()-> presenter.onClickListenerBinding(student)}"/>
```
lambda表达式实际上也是创建了匿名函数，然后再这个匿名函数中调用了Presenter中的方法，只不过省去了匿名函数的申明，只保留最为核心的逻辑部分。

此外，lambd表达式支持传入Context参数，比如Presenter中定义这样的函数

```
public void onStudentLongClick(Student student, Context context){
	Toast.makeText(LambdaActivity.this, "LongClick: "+student.toString(), Toast.LENGTH_SHORT).show();
}
```
Button中可以这样调用，Context参数会被自动生成：
```
<Button
	......
    android:longClickable="true"
    android:onClick="@{() -> presenter.onStudentLongClick(student,context)}"
    ......
            />
```


##4. DataBinding所支持的表达式
为了扩展更为强大的功能，DataBinding在使用变量时支持一下表达式：
- 运算符：Mathematical + - / * %
- 字符串连接：String concatenation +
- 逻辑运算：Logical && ||
- 位操作： Binary & | ^
- Unary + - ! ~
- 移位操作：Shift >> >>> <<
- 比较操作：Comparison == > < >= <=
- 类型判断：instanceof
- Grouping ()
- Literals - character, String, numeric, null
- 类型转化：Cast
- Method calls
- Field access
- Array access []
- Ternary operator ?:

但不支持的表达式：
- this
- super
- new
- Explicit generic invocation

###Null Coalescing 运算符
DataBinding对于引用变量是否为空，也有特殊的支持，使用符号**？？**。

```
android:text="@{student.firstName ??student.lastName}"
```

就等价于

```
android:text="@{student.firstName != null ? student.firstName : student.lastName}"
```

##5. 在include文件里使用BataBinding
为了让Xml布局文件更为简洁易懂，对于重复使用的布局可以单独建立布局文件，然后使用include导入。DataBinding也考虑到了这种使用情况。可以使用bind:variable_name导入数据变量。

```
<!--当你使用include的时候，你可以使用命名空间和属性中的变量名将数据传送到另一个布局中去-->  
<include  
    layout="@layout/include_show_name"  
    bind:presenter="@{presenter}"
    bind:student="@{student}"
    bind:visibility="@{visibility}" /> 
```
需要注意的是：
1. 一定要使用app命名空间`xmlns:app="http://schemas.android.com/apk/res-auto"`
2. 必须在ViewGroup的根布局中导入布局文件，如果在非根节点的ViewGroup中使用include会导致错误；

##6. 在SubView中的使用
在SubView的使用方式和在Include中使用是类似的。
```
<ViewStub
	bind:visibility="@{visibility}"
	android:id="@+id/view_stub"
    android:layout_width="368dp"
    android:layout_height="wrap_content"
    android:layout="@layout/viewstub"
/>
```
然后在Activity中通过DataBindingUtil获得SubView对象，然后填充View;
```
//viewstub inflate;
View inflate = mBinding.viewStub.getViewStub().inflate();
```

##7. 双向绑定
上面提的内容都是单向绑定，也就是把后台的数据绑定到前台显示出来。DataBinding如果只有如此功能，也不足为奇，最重要的功能点还是还在于*双向绑定*，前台显示的数据改变也会自动影响后台的数据。

首先我们新建一个数据对象类
```
public class FormModel extends BaseObservable {
	private String name;
	private String password;
	
	@Bindable //该注解表示数据绑定
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		//通知name属性发生改变
		notifyPropertyChanged(BR.name);
	}
	
	//注解标志该属性是绑定的
	@Bindable
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		//通知有属性发生改变
		notifyPropertyChanged(BR.password);
	}

	public FormModel(String name, String password) {
		this.name = name;
		this.password = password;

	}
}
```
该类继承了**BaseObservable**，在getter方法之前使用*@Bindable*，标记该属性就是需要绑定的，会在BR类中生成对应的ID；在setter方法中`notifyPropertyChanged(BR.ID)`，通知对应ID的属性发生了改变。

然后在XML中使用**“@={variable}”**表示数据绑定，如下。
```
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"        
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="model"
            type="com.example.administrator.databindingdemo.FormModel"/>
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="com.example.administrator.databindingdemo.TowWayBindingActivity">
        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Name"
            android:text="@={model.name}"/>
        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="pwd"
            android:text="@={model.password}"/>
        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{model.name}"/>
    </LinearLayout>
</layout>
```
最后在Activity中传入变量。
```
public class TowWayBindingActivity extends AppCompatActivity {

	ActivityTowWayBindingBinding mBinding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.
			setContentView(this,R.layout.activity_tow_way_binding);
		FormModel model = new FormModel("user", "12345");
		mBinding.setModel(model);
		
		//属性改变监听器
		model.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
			@Override
			public void onPropertyChanged(Observable sender, int propertyId) {
				if(BR.name == propertyId)
					Toast.makeText(TowWayBindingActivity.this, "姓名改变",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
```
这是运行程序就会发现，在前端输入姓名时，其他绑定**name**属性的地方也会改变，说明后台数据也响应改变了。

##8. 表达式链与隐式更新

当多个View的属性相互关联式时，可以通过表达式链实现隐式更新，比如：
```
<CheckBox android:id="@+id/check_box"/>
<ImageView android:visibility="check_box.checked?View.VISIBILE:View.GONE"/>
```

##9. 添加动画
数据绑定必然涉及到UI的变化，默认情况下往往都是很生硬的UI变化，没有任何过度。DataBinding也提供办法来插入动画。

实现方法也很简单，就是设置一个重新绑定时的回调函数**addOnRebindCallback**，在函数中设置当前View的UI变化时开启一定延时。

```
public class AnimationActivity extends AppCompatActivity {
	ActivityAnimationBinding mBinding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this,R.layout.activity_animation);
		mBinding.setPresenter(new AnimatorPresenter());
		//设置重绑定回调函数
		mBinding.addOnRebindCallback(new OnRebindCallback() {
			@Override
			public boolean onPreBind(ViewDataBinding binding) {
				ViewGroup viewGroup = (ViewGroup) binding.getRoot();
				//开启延时位移动画			
				TransitionManager.beginDelayedTransition(viewGroup);
				return true;
			}
		});
	}
	public class AnimatorPresenter{
		public void onCheckedChanged(boolean isChecked){
			mBinding.setShowImage(isChecked);
		}
	}
}
```
在XML中正常使用DataBinding
```
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <import type="android.view.View"/>
        <variable
            name="presenter"
            type="com.example.administrator.databindingdemo.AnimationActivity.AnimatorPresenter"/>
        <variable
            name="showImage"
            type="Boolean"/>
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="com.example.administrator.databindingdemo.AnimationActivity">
        <ImageView
            android:contentDescription="Animation Show"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:src="@drawable/default_avatar"
            android:visibility="@{showImage?View.VISIBLE:View.GONE}"/>

        <CheckBox
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="showImage"
            android:onCheckedChanged = "@{(v,isChecked)->presenter.onCheckedChanged(isChecked)}"/>
    </LinearLayout>
</layout>
```
这样图片在显示或者出现的过程中都会添加一个CheckBox位移匀速渐变的动画效果。

需要说明的是，虽然布局文件中**CheckBox**原本并没有**onCheckedChanged**属性，但是CheckBox类有对应的属性，并且设有**setOnCheckedChanged**方法来设置该属性，所以也是可以进行绑定的。

按照惯例，给出Demo源码： 
https://github.com/sunrongxin7666 
欢迎大家指正批评

需要说明的是，由于篇幅的限制，*还有动态绑定和在RecyclerView中使用没有介绍*，如果感兴趣请读者参考一下两篇文章，基于以上的内容，相信读者不会很难理解。
[Android DataBinding完全解析](http://blog.csdn.net/weiwozhiyi/article/details/52181532)
[精通 Android Data Binding](https://github.com/LyndonChin/MasteringAndroidDataBinding)
