
Kotlin基本语法准则:
在Kotlin中常量用 val 声明，变量用 var 声明；
关键字在前面，类型以冒号 :隔开在后面，也可以省略直接赋值；
类型后带问号 ? 表示可为空类型(默认空类型安全)；
常量 val 延迟加载 by lazy{} ；
默认是线程安全的，关闭线程安全 lazy(LazyThreadSafetyMode.NONE){} ；
变量 var 延迟加载 lateinit ;
内部类和参数默认为public，而在Java中为private
类默认为不可继承(final)，想要可被继承要声明为 open 或 abstract
取消了static关键字，静态方法和参数统一写在 companion object 块
internal模块内可见，inner内部类

===============================================
有时 getter/setter 方法比较复杂，这就需要自定义 getter/setter 了，实现一个Java中常用的单例，这里只为了展示，单例在Kotlin有更简单的方法实现，只要在 package 级别创建一个 object 即可

class User {
    companion object {//静态方法和参数统一写在 companion object 块
         //volatile不保证原子操作，所以，很容易读到脏数据。在两个或者更多的线程访问的成员变量上使用volatile
        @Volatile var instance: User? = null
            get() {
                if (field == null) {
                    synchronized(User::class.java) {
                        if (field == null)
                            field = User()
                    }
                }
                return field
            }
    }

    var name: String? = null
    var age: String? = null
}

=============================================

条件语句
if...else 正常使用，不过移除了 switch 用更强大的 when 替代，when子式可以是各种返回Boolean的表达式
val x = 7
when (x) {
  in 1..5 -> print("x is in the range")
  in validNumbers -> print("x is valid")
  !in 10..20 -> print("x is outside the range")
  else -> print("none of the above")
}

===================================================
循环语句
while 和 do...while 同Java并无区别， for 则有很大改变并多出了几个变种
fun main(args: Array<String>) {
    var list = ArrayList<String>()
    add(list)
    list.forEachIndexed { i, s ->
    print(list[i])
    print(s)
    }
    println()
    //如果没有指定函数的返回值，它就会返回Unit，与Java中的void类似，但是Unit是一个真正的对象。当然也可以指定任何其它的返回类型：
    list.forEachIndexed(object :(Int,String) -> Unit{
    override fun invoke(i: Int, s: String) {
        print(list[i])
        print(s)
    }
})
}
//递增for (int i = 0; i < list.size(); i++)
for (i in list.indices) {
   print(list[i])
}
//递增for (int i = 2; i < list.size(); i++)
for (i in 2..list.size-1) {
   print(list[i])
}
//递减for (int i = list.size(); i >= 0; i--)
for (i in list.size downTo 0) {
    print(list[i])
}
//操作列表内的对象
for (item in list) {
    print(item)
}
//加强版
for((i,item) in list.withIndex()){
    print(list[i])
    print(item)
}
//变种版
list.forEach {
    print(it)
}

list.forEachIndexed { i, s ->
    print(list[i])
    print(s)
}

list.forEachIndexed(object :(Int,String) -> Unit{
    override fun invoke(i: Int, s: String) {
        print(list[i])
        print(s)
    }
})
fun add(list:MutableList<String>) {
        for (i in 0..4) {
            list.add(i.toString() + "")
        }
    }
===============================================
集合操作

  list转map(associateBy)

  场景：订单列表转换成以 id为key 的订单map

val mainOrders = orderDao!!.queryUserOrder(param)
val orderMap = mainOrders.associateBy { it.id }.toMap()
  map的key或者value转换

  假如一个map的key是String，需要转换成Long；或者map的value是一个对象，要转成另一个对象。按照标准Java写法，可以要new一个新的map，然后循环老的map，在kotlin中，一行代码搞定

val map = mutableMapOf(1 to 1, 2 to 2)
val newMap = map.mapKeys { "key_${it.key}" }.mapValues { "value_${it.value}" }
println(newMap)
//打印结果 {key_1=value_1, key_2=value_2}
val pair = Pair("ss","sg")
 val map = mapOf(pair)
val map1=map.mapKeys { entry -> "${entry.value}!"  }
    for((key,value) in map1){
        println("map1:key=$key")
        println("map1:value=$value")
    }
        val map2 =map.mapKeys { (key, value) -> "$value"  }
    for((key,value) in map2){
        println("map2:key=$key")
        println("map2:value=$value")
    }
        val map3=map.mapValues { entry -> "${entry.value}!" }
    for((key,value) in map3){
        println("map3:key=$key")
        println("map3:value=$value")
    }
        val map4=map.mapValues { (key, value) -> "$value" }
    for((key,value) in map4){
        println("map4:key=$key")
        println("map4:value=$value")
    }
打印结果:
map1:key=sg!
map1:value=sg
map2:key=sg
map2:value=sg
map3:key=ss
map3:value=sg!
map4:key=ss
map4:value=sg

===================================================
1、可变长参数函数
函数的变长参数可以用 vararg 关键字进行标识：
fun vars(vararg v:Int){
    for(vt in v){
        print(vt)
    }
}

// 测试
fun main(args: Array<String>) {
    vars(1,2,3,4,5)  // 输出12345
}

=====================================================
2、lambda(匿名函数)
lambda表达式使用实例：
// 测试
fun main(args: Array<String>) {
    val sumLambda: (Int, Int) -> Int = {x,y -> x+y}
    println(sumLambda(1,2))  // 输出 3
}

============================================
3、字符串模板
$ 表示一个变量名或者变量值
$varName 表示变量值
${varName.fun()} 表示变量的方法返回值:
var a = 1
// 模板中的简单名称：
val s1 = "a is $a"

a = 2
// 模板中的任意表达式：
val s2 = "${s1.replace("is", "was")}, but now is $a"

==============================================
4、NULL检查机制
Kotlin的空安全设计对于声明可为空的参数，在使用时要进行空判断处理，有两种处理方式，字段后加!!像Java一样抛出空异常，另一种字段后加?可不做处理返回值为 null或配合?:做空判断处理
//类型后面加?表示可为空
var age: String? = "23"
//抛出空指针异常
val ages = age!!.toInt()
//不做处理返回 null
val ages1 = age?.toInt()
//age为空返回-1
val ages2 = age?.toInt() ?: -1

==========================================

5、类型检测及自动类型转换
我们可以使用 is 运算符检测一个表达式是否某类型的一个实例(类似于Java中的instanceof关键字)。
fun getStringLength(obj: Any): Int? {
  if (obj is String) {
    // 做过类型判断以后，obj会被系统自动转换为String类型
    return obj.length
  }

  //在这里还有一种方法，与Java中instanceof不同，使用!is
  // if (obj !is String){
  //   // XXX
  // }

  // 这里的obj仍然是Any类型的引用
  return null
}
或者
fun getStringLength(obj: Any): Int? {
  if (obj !is String)
    return null
  // 在这个分支中, `obj` 的类型会被自动转换为 `String`
  return obj.length
}
甚至还可以
fun getStringLength(obj: Any): Int? {
  // 在 `&&` 运算符的右侧, `obj` 的类型会被自动转换为 `String`
  if (obj is String && obj.length > 0)
    return obj.length
  return null
}

====================================================
6、区间
区间表达式由具有操作符形式 .. 的 rangeTo 函数辅以 in 和 !in 形成。
区间是为任何可比较类型定义的，但对于整型原生类型，它有一个优化的实现。以下是使用区间的一些示例:
for (i in 1..4) print(i) // 输出“1234”

for (i in 4..1) print(i) // 什么都不输出

if (i in 1..10) { // 等同于 1 <= i && i <= 10
    println(i)
}

// 使用 step 指定步长
for (i in 1..4 step 2) print(i) // 输出“13”

for (i in 4 downTo 1 step 2) print(i) // 输出“42”


// 使用 until 函数排除结束元素
for (i in 1 until 10) {   // i in [1, 10) 排除了 10
     println(i)
}
实例测试
fun main(args: Array<String>) {
    print("循环输出：")
    for (i in 1..4) print(i) // 输出“1234”
    println("\n----------------")
    print("设置步长：")
    for (i in 1..4 step 2) print(i) // 输出“13”
    println("\n----------------")
    print("使用 downTo：")
    for (i in 4 downTo 1 step 2) print(i) // 输出“42”
    println("\n----------------")
    print("使用 until：")
    // 使用 until 函数排除结束元素
    for (i in 1 until 4) {   // i in [1, 4) 排除了 4
        print(i)
    }
    println("\n----------------")
}
输出结果：
循环输出：1234
----------------
设置步长：13
----------------
使用 downTo：42
----------------
使用 until：123
----------------