package com.kimonik.utilsmodule.intterview;

/**
 * * ===============================================================
 * name:             Interview
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/3/9
 * method:
 * <p>
 * <p>
 * description：  练习题类
 * history：
 * *==================================================================
 */

public class Interview {
    /**
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public static boolean find(int[][] array, int target) {
        if (array == null) {
            return false;
        }
        int rows = array.length;//
        int columns = array[0].length;
        int row = 0;
        int column = columns - 1;
        while (row < rows && column >= 0) {//从每一维度的最大值开始查询
            int temp = array[row][column];
            if (target == temp) {
                return true;
            } else if (target > temp) {//行加1
                row++;
                if (row < rows) {
                    column = array[row].length - 1;//每行的值不固定
                } else {
                    return false;
                }
            } else if (target < temp) {//列减一
                column--;
            }
        }
        return false;
    }

    /**
     * 请实现一个函数，将一个字符串中的空格替换成“%20”。
     * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */

    public String replaceSpace(StringBuffer str) {
//        if (str == null) return null;
//        return str.toString().replace(" ", "%20");

        if (str == null) {
            return null;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.replace(i, i + 1, "%20");
            }
        }
        return str.toString();
    }
//    /**
//     * 输入一个链表，从尾到头打印链表每个节点的值。
//     */
//    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
//        Stack<Integer> stack = new Stack<>();
//        while (listNode != null) {
//            stack.push(listNode.val);
//            listNode = listNode.next;
//        }
//
//        ArrayList<Integer> list = new ArrayList<>();
//        while (!stack.isEmpty()) {
//            list.add(stack.pop());
//        }
//        return list;
//    }

//    /**
//     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果
//     * 中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，
//     * 则重建二叉树并返回。
//     */
//
//
//    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
//        if (pre == null || in == null) {
//            return null;
//        }
//        int len = pre.length;
//        if (len == 0) {
//            return null;
//        }
//        int val = pre[0];
//        int rootPositionInLDR = -1;
//        TreeNode root = new TreeNode(val);
//        for (int i = 0; i < len; i++) {
//            if (in[i] == val) {
//                rootPositionInLDR = i;
//            }
//        }
//        if (rootPositionInLDR == -1) {
//            return null;
//        }
//        // rebulid left child tree
//        int leftChildTreeNum = rootPositionInLDR;
//        int[] leftPre = new int[leftChildTreeNum];
////      for (int i = 0; i < leftChildTreeNum; i++) {
////          leftPre[i] = pre[i + 1];
////      }
//        System.arraycopy(pre,1,leftPre,0,leftChildTreeNum);
//
//
//        int[] leftIn = new int[leftChildTreeNum];
////      for (int i = 0; i < leftChildTreeNum; i++) {
////          leftIn[i] = in[i];
////      }
//        System.arraycopy(in,0,leftIn,0,leftChildTreeNum);
//        root.left = rebuildBinaryTree(leftPre, leftIn);
//
//
//
//        // rebulid right child tree
//        int rightChildTreeNum = len - rootPositionInLDR - 1;
//        int[] rightPre = new int[rightChildTreeNum];
//        System.arraycopy(pre,rootPositionInLDR + 1,rightPre,0,rightChildTreeNum);
////      for (int i = 0; i < rightChildTreeNum; i++) {
////          rightPre[i] = pre[i + rootPositionInLDR + 1];
////      }
//        int[] rightIn = new int[rightChildTreeNum];
////      for (int i = 0; i < rightChildTreeNum; i++) {
////          rightIn[i] = in[i + rootPositionInLDR + 1];
////      }
//        System.arraycopy(in,rootPositionInLDR + 1,rightIn,0,rightChildTreeNum);
//        root.right = rebuildBinaryTree(rightPre, rightIn);
//        return root;
////        if (pre == null || in == null) {
////            return null;
////        }
////
////        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<Integer, Integer>();
////        for (int i = 0; i < in.length; i++) {
////            map.put(in[i], i);
////        }
////        return preIn(pre, 0, pre.length - 1, in, 0, in.length - 1, map);
////    }
////
////    public TreeNode preIn(int[] p, int pi, int pj, int[] n, int ni, int nj, java.util.HashMap<Integer, Integer> map) {
////
////        if (pi > pj) {
////            return null;
////        }
////        TreeNode head = new TreeNode(p[pi]);
////        int index = map.get(p[pi]);
////        head.left = preIn(p, pi + 1, pi + index - ni, n, ni, index - 1, map);
////        head.right = preIn(p, pi + index - ni + 1, pj, n, index + 1, nj, map);
////        return head;
//    }

//    /**
//     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
//     */
//    Stack<Integer> stack1 = new Stack<Integer>();
//    Stack<Integer> stack2 = new Stack<Integer>();
//
//    public void push(int node) {
//        stack1.push(node);
//    }
//
//    public int pop() {
//        while(!stack1.isEmpty()){
//            stack2.push(stack1.pop());
//        }
//        int first=stack2.pop();
//        while(!stack2.isEmpty()){
//            stack1.push(stack2.pop());
//        }
//        return first;
//    }

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     */
    public int minNumberInRotateArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int i = 0;
        int a = array[i];
        while (i < array.length - 1 && array[i] <= array[i + 1]) {
            i++;
        }
        int j = i + 1;
        if (j == array.length) {
            j = 0;
        }
        return array[j];

//        if (array.length == 1) {
//            return array[0];
//        }
//
//        for (int i = 0; i < array.length - 1; i++) {
//            if (array[i] > array[i + 1]) {
//                return array[i + 1];
//            } else {
//                if (i == array.length - 2) {
//                    return array[0];
//                }
//            }
//        }
//
//        return 0;

    }

    /**
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项。
     * n<=39
     * 1、1、2、3、5、8、13、21、34
     */
    public int Fibonacci(int n) {
        if (n > 39 || n < 1) return 0;
        if (n == 1) return 1;
        if (n == 2) return 1;
        int sum = 0;
        int first = 1;
        int second = 1;
        for (int i = 0; i < n - 2; i++) {
            sum = first + second;
            first = second;
            second = sum;
        }
        return sum;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     * 台阶每加一步,相当于却一步时跳一步,这一种方式加缺两步时跳两步这一种中方式
     * 斐波那契数列  1 2 3 5 8 13
     * 因为内一个位置只有两种方式,所以前后放置同一个元素时会重复
     * a-abc abc-a
     * abc-a acb-a bac-a bca-a acb-a cba-a
     * a-abc a-acb a-bac  a-acb
     * <p>
     * abc-b acb-b bac-b bca-b acb-b cba-b
     * b-abc b-acb b-bac b-bca b-acb b-cba
     * <p>
     * abc-c acb-c bac-c bca-c acb-c cba-c
     * a-bca a-cba
     */
    public int JumpFloor(int target) {
        if (target < 1) return 0;
        if (target == 1) return 1;
        if (target == 2) return 2;
        int floorAfter = 2;
        int floorBefore = 1;
        int floorN = 0;
        for (int i = 3; i <= target; i++) {
            floorN = floorAfter + floorBefore;
            floorBefore = floorAfter;
            floorAfter = floorN;
        }
        return floorN;

    }

    /**
     * 001.fragment的生命周期
     */
    private void aaa() {
        /**
         * Fragment每个生命周期方法的意义、作用（注意红色的不是生命周期方法）：
         setUserVisibleHint()：设置Fragment可见或者不可见时会调用此方法。在该方法里面可以通过调用getUserVisibleHint()获得Fragment的状态是可见还是不可见的，如果可见则进行懒加载操作。
         onAttach()：执行该方法时，Fragment与Activity已经完成绑定，该方法有一个Activity类型的参数，代表绑定的Activity，这时候你可以执行诸如mActivity = activity的操作。
         onCreate()：初始化Fragment。可通过参数savedInstanceState获取之前保存的值。
         onCreateView()：初始化Fragment的布局。加载布局和findViewById的操作通常在此函数内完成，但是不建议执行耗时的操作，比如读取数据库数据列表。
         onActivityCreated()：执行该方法时，与Fragment绑定的Activity的onCreate方法已经执行完成并返回，在该方法内可以进行与Activity交互的UI操作，所以在该方法之前Activity的onCreate方法并未执行完成，如果提前进行交互操作，会引发空指针异常。
         onStart()：执行该方法时，Fragment由不可见变为可见状态。
         onResume()：执行该方法时，Fragment处于活动状态，用户可与之交互。
         onPause()：执行该方法时，Fragment处于暂停状态，但依然可见，用户不能与之交互。
         onSaveInstanceState()：保存当前Fragment的状态。该方法会自动保存Fragment的状态，比如EditText键入的文本，即使Fragment被回收又重新创建，一样能恢复EditText之前键入的文本。
         onStop()：执行该方法时，Fragment完全不可见。
         onDestroyView()：销毁与Fragment有关的视图，但未与Activity解除绑定，依然可以通过onCreateView方法重新创建视图。通常在ViewPager+Fragment的方式下会调用此方法。
         onDestroy()：销毁Fragment。通常按Back键退出或者Fragment被回收时调用此方法。
         onDetach()：解除与Activity的绑定。在onDestroy方法之后调用。

         Fragment生命周期执行流程（注意红色的不是生命周期方法）：
         Fragment创建：setUserVisibleHint()->onAttach()->onCreate()->onCreateView()->onActivityCreated()->onStart()->onResume()；
         Fragment变为不可见状态（锁屏、回到桌面、被Activity完全覆盖）：onPause()->onSaveInstanceState()->onStop()；
         Fragment变为部分可见状态（打开Dialog样式的Activity）：onPause()->onSaveInstanceState()；
         Fragment由不可见变为活动状态：onStart()->OnResume()；
         Fragment由部分可见变为活动状态：onResume()；
         退出应用：onPause()->onStop()->onDestroyView()->onDestroy()->onDetach()（注意退出不会调用onSaveInstanceState方法，因为是人为退出，没有必要再保存数据）；
         Fragment被回收又重新创建：被回收执行onPause()->onSaveInstanceState()->onStop()->onDestroyView()->onDestroy()->onDetach()，重新创建执行onAttach()->onCreate()->onCreateView()->onActivityCreated()->onStart()->onResume()->setUserVisibleHint()；
         横竖屏切换：与Fragment被回收又重新创建一样。
         */
    }


    /**
     * 002.事件分发机制，以及涉及到的设计模式
     */

    private void aab() {
/**
 * Touch事件分发中只有两个主角:ViewGroup和View。Activity的Touch事件事实上是调用它内部的
 * ViewGroup的Touch事件，可以直接当成ViewGroup处理。

 View在ViewGroup内，ViewGroup也可以在其他ViewGroup内，这时候把内部的ViewGroup当成View来分析。

 ViewGroup的相关事件有三个：onInterceptTouchEvent、dispatchTouchEvent、onTouchEvent。View的相
 关事件只有两个：dispatchTouchEvent、onTouchEvent。

 先分析ViewGroup的处理流程：首先得有个结构模型概念：ViewGroup和View组成了一棵树形结构，
 最顶层为Activity的ViewGroup，下面有若干的ViewGroup节点，
 每个节点之下又有若干的ViewGroup节点或者View节点，依次类推


 当一个Touch事件(触摸事件为例)到达根节点，即Acitivty的ViewGroup时，它会依次下发，下发的过程是调用子
 View(ViewGroup)的dispatchTouchEvent方法实现的。简单来说，就是ViewGroup遍历它包含着的子View，
 调用每个View的dispatchTouchEvent方法，而当子View为ViewGroup时，又会通过调用ViwGroup的dispatchTouchEvent方
 法继续调用其内部的View的dispatchTouchEvent方法。上述例子中的消息下发顺序是这样的：①-②-⑤-⑥-⑦-③-④。
 dispatchTouchEvent方法只负责事件的分发，它拥有boolean类型的返回值，当返回为true时，顺序下发会中断。
 在上述例子中如果⑤的dispatchTouchEvent返回结果为true，那么⑥-⑦-③-④将都接收不到本次Touch事件。

 一般情况下，我们不该在普通View内重写dispatchTouchEvent方法，因为它并不执行分发逻辑。
 当Touch事件到达View时，我们该做的就是是否在onTouchEvent事件中处理它。

 那么，ViewGroup的onTouchEvent事件是什么时候处理的呢？当ViewGroup所有的子View都返回false时，
 onTouchEvent事件便会执行。由于ViewGroup是继承于View的，
 它其实也是通过调用View的dispatchTouchEvent方法来执行onTouchEvent事件。



 在目前的情况看来，似乎只要我们把所有的onTouchEvent都返回false，就能保证所有的子控件都响应本次Touch事件了。
 但必须要说明的是，这里的Touch事件，只限于Acition_Down事件，即触摸按下事件,而Aciton_UP和Action_MOVE却
 不会执行。事实上，一次完整的Touch事件，应该是由一个Down、一个Up和若干个Move组成的。Down方式通过
 dispatchTouchEvent分发，分发的目的是为了找到真正需要处理完整Touch请求的View。当某个View或者
 ViewGroup的onTouchEvent事件返回true时，便表示它是真正要处理这次请求的View，之后的Aciton_
 UP和Action_MOVE将由它处理。
 当所有子View的onTouchEvent都返回false时，这次的Touch请求就由根ViewGroup，即Activity自己处理了。
 ViewGroup还有个onInterceptTouchEvent，看名字便知道这是个拦截事件。这个拦截事件需要分两种情况来说明：

 1.假如我们在某个ViewGroup的onInterceptTouchEvent中，将Action为Down的Touch事件返回true，
 那便表示将该ViewGroup的所有下发操作拦截掉，这种情况下，mTarget会一直为null，
 因为mTarget是在Down事件中赋值的。由于mTarge为null，该ViewGroup的onTouchEvent事件被执行。
 这种情况下可以把这个ViewGroup直接当成View来对待。

 2.假如我们在某个ViewGroup的onInterceptTouchEvent中，将Acion为Down的Touch事件都返回false，
 其他的都返回True，这种情况下，Down事件能正常分发，若子View都返回false，那mTarget还是为空，
 无影响。若某个子View返回了true，mTarget被赋值了，在Action_Move和Aciton_UP分发到该ViewGroup时，
 便会给mTarget分发一个Action_Delete的MotionEvent，同时清空mTarget的值，
 使得接下去的Action_Move(如果上一个操作不是UP)将由ViewGroup的onTouchEvent处理。

 情况一用到的比较多，情况二个人还未找到使用场景。

 从头到尾总结一下：

 1.Touch事件分发中只有两个主角:ViewGroup和View。ViewGroup包含onInterceptTouchEvent、
 dispatchTouchEvent、onTouchEvent三个相关事件。View包含dispatchTouchEvent、
 onTouchEvent两个相关事件。其中ViewGroup又继承于View。

 2.ViewGroup和View组成了一个树状结构，根节点为Activity内部包含的一个ViwGroup。

 3.触摸事件由Action_Down、Action_Move、Aciton_UP组成，其中一次完整的触摸事件中，
 Down和Up都只有一个，Move有若干个，可以为0个。

 4.当Acitivty接收到Touch事件时，将遍历子View进行Down事件的分发。
 ViewGroup的遍历可以看成是递归的。分发的目的是为了找到真正要处理本次完整触摸事件的View，
 这个View会在onTouchuEvent结果返回true。

 5.当某个子View返回true时，会中止Down事件的分发，同时在ViewGroup中记录该子View。
 接下去的Move和Up事件将由该子View直接进行处理。由于子View是保存在ViewGroup中的，
 多层ViewGroup的节点结构时，上级ViewGroup保存的会是真实处理事件的View所在的ViewGroup对象:
 如ViewGroup0-ViewGroup1-TextView的结构中，TextView返回了true，它将被保存在ViewGroup1中，
 而ViewGroup1也会返回true，被保存在ViewGroup0中。当Move和UP事件来时，
 会先从ViewGroup0传递至ViewGroup1，再由ViewGroup1传递至TextView。

 6.当ViewGroup中所有子View都不捕获Down事件时，将触发ViewGroup自身的onTouch事件。
 触发的方式是调用super.dispatchTouchEvent函数，即父类View的dispatchTouchEvent方法。
 在所有子View都不处理的情况下，触发Acitivity的onTouchEvent方法。

 7.onInterceptTouchEvent有两个作用：1.拦截Down事件的分发。2.中止Up和Move事件向目标View传递，
 使得目标View所在的ViewGroup捕获Up和Move事件。
 * 从 Android 源码 发现设计模式
 前面已经写过了事件的分发机制，以及源码的分析了。每当用户接触到了屏幕的时候，
 Android会将对应的事件包装成一个事件从对象ViewTree的顶部从上到下的开始分发传递。
 而在ViewGroup中，ViewGroup中执行事件的派发分发是通过dispatchTouchEvent。

 在这个方法中，我们可以很清楚的看到ViewGroup就会遍历所有的子View。拿到子View的时候，
 源码中做了几个判断，判断这个子View是否符合条件。如果不满足则直接选取下一个子View。
 拿到符合条件的子View，那么将调用 dispatchTransformedTouchEvent 这个方法。
 在dispatchTransformedTouchEvent方法中就调用了child.dispatchTouchEvent(event)。

 从上源码中可以的知到最重要的一点就是，ViewGroup的事件投递的递归调用就类似一个责任链，
 不断的从子View中寻找能处理这个事件的子View，一旦找到了这个责任者，
 那么将由这个责任者持有并且消费这个事件。从View中的 onTouchEvent
 方法的返回值可以很清楚的看到这一点，当onTouchEvent 返回 false 代表 ，
 那么意味着这个不是该事件的负责人，当返回了true，此时会持有这个事件，并不向外再传递了。

 责任链模式 介绍
 从ViewGroup对于事件的分发来说，一个传递给一个，形成了一个链子，最后从链子中找到一
 个来处理这个事件。把这种形式抽象出来，其实就是责任链模式，我们把多个节点首尾相连所构成
 的模式称为链。对于这种构成，把每一个节点都看做一个对象，每个对象拥有不同的处理逻辑，然
 后将一个责任从链子的首端出发，沿着链子依次传递给每个节点的对象，直到有个对象处理这个责任。

 责任链模式 定义
 使多个对象都有机会处理请求，从而避免了请求的发送者和接收者之间的耦合关系。
 将这些对象连成一条链，并沿着这条链传递该请求，直到有对象处理他为止。

 责任链模式 使用场景
 使用场景就和ViewGroup一样，多个对象处理一个请求，但是具体由哪个对象处理，是动态决定的。

 * 责任链模式：

 优点：显而易见，可以对链中请求者和处理者关系解耦，提高了代码的灵活性。

 缺点：每次处理都要从头开始遍历，如果处理者太多牌，那么遍历必然会影响性能。
 */
    }

    /**
     * 003.
     * onMeasure的测量模式与特点
     */
    private void aac() {
        /**
         *1. onMeasure什么时候会被调用
           onMeasure方法的作用时测量空间的大小，什么时候需要测量控件的大小呢？我们举个栗子，
         做饭的时候我们炒一碗菜，炒菜的过程我们并不要求知道这道菜有多少分量，只有在菜做熟了
         我们要拿个碗盛放的时候，我们才需要掂量拿多大的碗盛放，这时候我们就要对菜的分量进行估测。
           而我们的控件也正是如此，创建一个View(执行构造方法)的时候不需要测量控件的大小，
         只有将这个view放入一个容器（父控件）中的时候才需要测量，而这个测量方法就是父控件唤起调用的。
         当控件的父控件要放置该控件的时候，父控件会调用子控件的onMeasure方法询问子控件：
         “你有多大的尺寸，我要给你多大的地方才能容纳你？”，然后传入两个参数
         （widthMeasureSpec和heightMeasureSpec），这两个参数就是父控件告诉子控件可获得的空间
         以及关于这个空间的约束条件（好比我在思考需要多大的碗盛菜的时候我要看一下碗柜里最大的碗有多大，
         菜的分量不能超过这个容积，这就是碗对菜的约束），子控件拿着这些条件就能正确的测量自身的宽高了。
         2. onMeasure方法执行流程
           上面说到onMeasure方法是由父控件调用的，所有父控件都是ViewGroup的子类，
         ViewGroup是一个抽象类，它里面有一个抽象方法onLayout，这个方法的作用就是摆放它所有的子控件
         （安排位置），因为是抽象类，不能直接new对象，所以我们在布局文件中可以使用View但是不能直接使用
         ViewGroup。

           在给子控件确定位置之前，必须要获取到子控件的大小（只有确定了子控件的大小才能正确的确定上
         下左右四个点的坐标），而ViewGroup并没有重写View的onMeasure方法，也就是说抽象类ViewGroup没有
         为子控件测量大小的能力，它只能测量自己的大小。但是既然ViewGroup是一个能容纳子控件的容器，
         系统当然也考虑到测量子控件的问题，所以ViewGroup提供了三个测量子控件相关的方法
         (measuireChildren\measuireChild\measureChildWithMargins)，只是在ViewGroup中没有调用它们，
         所以它本身不具备为子控件测量大小的能力，但是他有这个潜力哦。

           为什么都有测量子控件的方法了而ViewGroup中不直接重写onMeasure方法，
         然后在onMeasure中调用呢？因为不同的容器摆放子控件的方式不同，比如RelativeLayout，
         LinearLayout这两个ViewGroup的子类，它们摆放子控件的方式不同，有的是线性摆放，
         而有的是叠加摆放，这就导致测量子控件的方式会有所差别，所以ViewGroup就干脆不直接测量子控件，
         他的子类要测量子控件就根据自己的布局特性重写onMeasure方法去测量。这么看来ViewGroup提供的三个
         测量子控件的方法岂不是没有作用？答案是NO，既然提供了就肯定有作用，这三个方法只是按照一种
         通用的方式去测量子控件，很多ViewGruop的子类测量子控件的时候就使用了
         ViewGroup的measureChildxxx系列方法；还有一个作用就是为我们自定义ViewGroup提供方便咯，
         自定义ViewGroup我会在以后的博客中专门探讨，这里就不大费篇章了。

           测量的时候父控件的onMeasure方法会遍历他所有的子控件，挨个调用子控件的measure方法，
         measure方法会调用onMeasure，然后会调用setMeasureDimension方法保存测量的大小，
         一次遍历下来，第一个子控件以及这个子控件中的所有子控件都会完成测量工作；

         然后开始测量第二个子控件…；最后父控件所有的子控件都完成测量以后会调用
         setMeasureDimension方法保存自己的测量大小。值得注意的是，这个过程不只执行一次，
         也就是说有可能重复执行，因为有的时候，一轮测量下来，父控件发现某一个子控件的尺寸不符合要求，
         就会重新测量一遍。

         从源码中我们知道，MeasureSpec其实就是尺寸和模式通过各种位运算计算出的一个整型值，
         它提供了三种模式，还有三个方法（合成约束、分离模式、分离尺寸）。
         我们得到了这三种模式对应的意思和布局文件中的参数值的对应关系
         （父控件填充屏幕MATCH_PARENT的情况，如果其他情况，请参考下面getChildMeasureSpec方法的结果）：

         约束	布局参数	值	说明
         UNSPECIFIED(未指定)		0	父控件没有对子控件施加任何约束，
         子控件可以得到任意想要的大小（使用较少）。

         EXACTLY(完全)	match_parent/具体宽高值	1073741824
         父控件给子控件决定了确切大小，子控件将被限定在给定的边界里而忽略它本身大小。
         特别说明如果是填充父窗体，说明父控件已经明确知道子控件想要多大的尺寸了
         （就是剩余的空间都要了）栗子：碗柜最大的碗就这么大，菜有多少都只能盛到这个最大的碗里，
         盛不下的我就管不了了（吃掉或者倒掉）

         AT_MOST(至多)	wrap-content	-2147483648	子控件至多达到指定大小的值。
         包裹内容就是父窗体并不知道子控件到底需要多大尺寸（具体值），
         需要子控件自己测量之后再让父控件给他一个尽可能大的尺寸以便让内容全部显示
         但不能超过包裹内容的大小栗子：碗柜有各种大小的碗，菜少就拿小碗放，
         菜多就拿大碗放，但是不能浪费碗的容积，要保证碗刚好盛满菜

          measureChildren 就是遍历所有子控件挨个测量，最终测量子控件的方法就是measureChild
         和measureChildWithMargins 了，我们先了解几个知识点：

         measureChildWithMargins跟measureChild的区别就是父控件支不支持margin属性
           支不支持margin属性对子控件的测量是有影响的，比如我们的屏幕是1080x1920的，
         子控件的宽度为填充父窗体，如果使用了marginLeft并设置值为100；
         在测量子控件的时候，如果用measureChild，计算的宽度是1080，
         而如果是使用measureChildWithMargins，计算的宽度是1080-100 = 980。

         怎样让ViewGroup支持margin属性？
           ViewGroup中有两个内部类ViewGroup.LayoutParams和ViewGroup. MarginLayoutParams，
         MarginLayoutParams继承自LayoutParams ，这两个内部类就是VIewGroup的布局参数类，
         比如我们在LinearLayout等布局中使用的layout_width\layout_hight等以“layout_ ”
         开头的属性都是布局属性。在View中有一个mLayoutParams的变量用来保存这个View的所有布局属性。

         LayoutParams和MarginLayoutParams 的关系：
         LayoutParams 中定义了两个属性（现在知道我们用的layout_width\layout_hight的来头了吧？）：
         MarginLayoutParams 是LayoutParams的子类，它当然也延续了layout_width\layout_hight 属性
         ，但是它扩充了其他属性：

         为什么LayoutParams 类要定义在ViewGroup中？
           大家都知道ViewGroup是所有容器的基类，一个控件需要被包裹在一个容器中，这个容器必须提供一种规则控制子控件的摆放，比如你的宽高是多少，距离那个位置多远等。所以ViewGroup有义务提供一个布局属性类，用于控制子控件的布局属性。

         为什么View中会有一个mLayoutParams 变量？
           我们在之前学习自定义控件的时候学过自定义属性，我们在构造方法中，初始化布局文件中的属性值，我们姑且把属性分为两种。一种是本View的绘制属性，比如TextView的文本、文字颜色、背景等，这些属性是跟View的绘制相关的。另一种就是以“layout_”打头的叫做布局属性，这些属性是父控件对子控件的大小及位置的一些描述属性，这些属性在父控件摆放它的时候会使用到，所以先保存起来，而这些属性都是ViewGroup.LayoutParams定义的，所以用一个变量保存着。

         怎样让ViewGroup支持margin属性？
         ②. getChildMeasureSpec方法
           measureChildWithMargins跟measureChild 都调用了这个方法，
         其作用就是通过父控件的宽高约束规则和父控件加在子控件上的宽高布局参数生成一个子控件的约束。

         我们知道View的onMeasure方法需要两个参数（父控件对View的宽高约束），
         这个宽高约束就是通过这个方法生成的。有人会问为什么不直接拿着子控件的宽高参数去测量子控件呢？
         打个比方，父控件的宽高约束为wrap_content，而子控件为match_perent，是不是很有意思，
         父控件说我的宽高就是包裹我的子控件，我的子控件多大我就多大，而子控件说我的宽高填充父窗体，
         父控件多大我就多大。最后该怎么确定大小呢？所以我们需要为子控件重新生成一个新的约束规则。
         只要记住，子控件的宽高约束规则是父控件调用getChildMeasureSpec方法生成。

         getChildMeasure方法代码不多，也比较简单，就是几个switch将各种情况考虑后生成一个子控件的新的宽高约束，
         这个方法的结果能够用一个表来概括：

         父控件的约束规则	子控件的宽高属性	子控件的约束规则	说明
         EXACTLY（父控件是填充父窗体，或者具体size值）	具体的size（20dip）/MATCH_PARENT
         EXACTLY	子控件如果是具体值，约束尺寸就是这个值，模式为确定的；子控件为填充父窗体，
         约束尺寸是父控件剩余大小，模式为确定的。
         WRAP-CONTENT	AT_MOST	子控件如果是包裹内容，约束尺寸值为父控件剩余大小 ，模式为至多
         AT_MOST（父控件是包裹内容）	具体的size（20dip）	EXACTLY	子控件如果是具体值，
         约束尺寸就是这个值，模式为确定的；
         MATCH_PARENT/WRAP_CONTENT	AT_MOST	子控件为填充父窗体或者包裹内容 ，
         约束尺寸是父控件剩余大小 ，模式为至多
         UNSPECIFIED（父控件未指定）	具体的size（20dip）	EXACTLY	子控件如果是具体值，
         约束尺寸就是这个值，模式为确定的；
         MATCH_PARENT/WRAP_CONTENT	UNSPECIFIED	子控件为填充父窗体或者包裹内容 ，约束尺寸0，模式为未指定

         进行了上面的步骤，接下来就是在measureChildWithMarginsh或者measureChild中
         调用子控件的measure方法测量子控件的尺寸了。

         ③. View的onMeasure
           View中onMeasure方法已经默认为我们的控件测量了宽高，我们看看它做了什么工作：


         从源码我们了解到：

         如果View的宽高模式为未指定，他的宽高将设置为android:minWidth/Height =”“值与背景宽高值中较大的一个；
         如果View的宽高 模式为 EXACTLY （具体的size ），最终宽高就是这个size值；
         如果View的宽高模式为EXACTLY （填充父控件 ），最终宽高将为填充父控件；
         如果View的宽高模式为AT_MOST （包裹内容），最终宽高也是填充父控件。
         也就是说如果我们的自定义控件在布局文件中，只需要设置指定的具体宽高，或者MATCH_PARENT 的情况，我们可以不用重写onMeasure方法。

         但如果自定义控件需要设置包裹内容WRAP_CONTENT ，我们需要重写onMeasure方法，为控件设置需要的尺寸；默认情况下WRAP_CONTENT 的处理也将填充整个父控件。

         ④. setMeasuredDimension
           onMeasure方法最后需要调用setMeasuredDimension方法来保存测量的宽高值，如果不调用这个方法，可能会产生不可预测的问题。


         这篇博客我们学习了onMeasure方法测量控件大小的流程，以及里面执行的一些细节，总结一下知识点：

         测量控件大小是父控件发起的
         父控件要测量子控件大小，需要重写onMeasure方法，然后调用measureChildren或者measureChildWithMargin方法
         on Measure方法的参数是通过getChildMeasureSpec生成的
         如果我们自定义控件需要使用wrap_content，我们需要重写onMeasure方法
         测量控件的步骤：
         父控件onMeasure->measureChildren`measureChildWithMargin->getChildMeasureSpec->
         子控件的measure->onMeasure->setMeasureDimension->
         父控件onMeasure结束调用setMeasureDimension`保存自己的大小
         这篇博客的内容比较简单，稍微有点绕的就是getChildMeasureSpec这个方法，
         我们可以结合我们平时写布局的经验，去推导约束规则，只要理解之后就不会感觉绕了。
         可能我写得不是很清楚，如果有什么建议欢迎留言，如果认为对你有帮助，那就点个赞呗~

         */

        }

    /**
004.
     线程间通信和进程间通信*/
    private void aad(){
/**
 * 当一个程序第一次启动的时候，Android会启动一个LINUX进程和一个主线程。
 * 默认的情况下，所有该程序的组件都将在该进程和线程中运行。
 * 同时，Android会为每个应用程序分配一个单独的LINUX用户。Android会尽量保留一个正在运行进程，
 * 只在内存资源出现不足时，Android会尝试停止一些进程从而释放足够的资源给其他新的进程使用，
 * 也能保证用户正在访问的当前进程有足够的资源去及时地响应用户的事件。线程是进程的有机组成部分，
 * 是CPU调度的基础。一般情况下，都有主线程和其他线程之分，只有主线程才可以刷新UI。
 * 应用程序启动后，将创建ActivityThread 主线程。

 不同包名的组件可以一定的方式运行在同一个进程中。

 一个Activity启动后，至少会有3个线程。一个主线程和2个binder线程。

 1.安卓线程间通信的方式有以下几种
 1）共享变量（内存）

 2）管道

 3）handle机制

 runOnUiThread(Runnable)

 view.post(Runnable)

 android 进程内的消息驱动机制---Handler,MessageQueue,Runnable,Looper

 Looper和Message的处理机制:首先在主线程中创建了一个handler对象,
 目的是为了处理从子线程发送过来的消息,然后当子线程有发送消息的需求时会使用Message对象,
 消息首先会被存储在Message queue消息队列中,主线程还有一个Looper消息轮询器,会循环遍历消息队列中的消息,
 当发现消息的时候会发送消息给handler处理(更新ui等操作),handler调用handleMessage处理完后将Message
 置为null以便回收.



 2进程间的通信
 进程间的通信：

 bind机制（IPC->AIDL)

 linux级共享内存

 boradcast

 Activity之间可以通过intent来传递数据



 3.安卓结束进程几种方式

 1)使用ActivityManager中的restartPackage(String packname)方法,这里清单文件里面要配置权限

 2)android.os.process.killProcess(int pid)只能终止本程序的进程

 3)System.exit()

 4)在android2.2版本之后则不能再使用restartPackage()方法，
 而应该使用killBackgroundProcesses()方法,同时应该配置权限

 5)利用反射调用forceStopPackage来结束
 Method forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
 forceStopPackage.setAccessible(true);
 forceStopPackage.invoke(am, yourpkgname);
 配置文件中需要添加定义：
 android:sharedUserId="android.uid.system"
 　　另外需要再在配置文件添加权限：
 <uses-permission android:name="android.permission.FORCE_STOP_PACKAGES"></uses-permission>
 6)使用Linux指令kill -9

 7)退出到主屏幕

 由于应用程序之间不能共享内存。在不同应用程序之间交互数据（跨进程通讯），在android SDK中提供了4种用于跨进程通讯的方式。这4种方式正好对应于android系统中4种应用程序组件：Activity、Content Provider、Broadcast和Service。其中Activity可以跨进程调用其他应用程序的Activity；Content Provider可以跨进程访问其他应用程序中的数据（以Cursor对象形式返回），当然，也可以对其他应用程序的数据进行增、删、改操 作；Broadcast可以向android系统中所有应用程序发送广播，而需要跨进程通讯的应用程序可以监听这些广播；Service和Content Provider类似，也可以访问其他应用程序中的数据，但不同的是，Content Provider返回的是Cursor对象，而Service返回的是Java对象，这种可以跨进程通讯的服务叫AIDL服务。





 Activity

 Activity的跨进程访问与进程内访问略有不同。虽然它们都需要Intent对象，

 但跨进程访问并不需要指定Context对象和Activity的 Class对象，
 而需要指定的是要访问的Activity所对应的Action（一个字符串）。
 有些Activity还需要指定一个Uri（通过 Intent构造方法的第2个参数指定）。

 在android系统中有很多应用程序提供了可以跨进程访问的Activity，例如，

 下面的代码可以直接调用拨打电话的Activity。

 Intent callIntent = new  Intent(Intent.ACTION_CALL, Uri.parse("tel:12345678" );
 startActivity(callIntent);



 Content Provider

 Android应用程序可以使用文件或SqlLite数据库来存储数据。
 Content Provider提供了一种在多个应用程序之间数据共享的方式（跨进程共享数据）。
 应用程序可以利用Content Provider完成下面的工作

 1. 查询数据
 2. 修改数据
 3. 添加数据
 4. 删除数据

 虽然Content Provider也可以在同一个应用程序中被访问，但这么做并没有什么意义。
 Content Provider存在的目的向其他应用程序共享数据和允许其他应用程序对数据进行增、删、改操作。
 Android系统本身提供了很多Content Provider，例如，音频、视频、联系人信息等等。
 我们可以通过这些Content Provider获得相关信息的列表。这些列表数据将以Cursor对象返回。
 因此，从Content Provider返回的数据是二维表的形式。



 广播（Broadcast）
 广播是一种被动跨进程通讯的方式。当某个程序向系统发送广播时，
 其他的应用程序只能被动地接收广播数据。这就象电台进行广播一样，
 听众只能被动地收听，而不能主动与电台进行沟通。
 在应用程序中发送广播比较简单。只需要调用sendBroadcast方法即可。
 该方法需要一个Intent对象。通过Intent对象可以发送需要广播的数据。





 Service


 1.利用AIDL Service实现跨进程通信

 这是我个人比较推崇的方式，因为它相比Broadcast而言，虽然实现上稍微麻烦了一点，
 但是它的优势就是不会像广播那样在手机中的广播较多时会有明显的时延，甚至有广播发送不成功的情况出现。

 注意普通的Service并不能实现跨进程操作，实际上普通的Service和它所在的应用处于同一个进程中，
 而且它也不会专门开一条新的线程，因此如果在普通的Service中实现在耗时的任务，需要新开线程。

 要实现跨进程通信，需要借助AIDL(Android Interface Definition Language)。
 Android中的跨进程服务其实是采用C/S的架构，因而AIDL的目的就是实现通信接口。


 */
    }



    /**

     005.ArrayList删除元素*/
    private void aae(){
        /**
         * Arraylist删除一个元素,有两种方法
         1.按下标删除  如:
         list.remove(0),
         list.remove(list.size() -1);

         2.按元素删除 如:
         list.remove(list.get(0)) 删除第一个元素
         list.remove(list.get(list.size()-1)) 删除最后一个元素

         删除后list的大小会改变,注意在for循环中remove时的陷阱
         */
    }

    /**

     006.写出你认为最优的懒汉式单例模式*/

    private void aaf(){
        /**
         * 懒汉模式：

         class Singleton {
         private volatile static Singleton singleton;
         private Singleton (){}
         public static Singleton getSingleton() {
         if (singleton == null) {
         synchronized (Singleton.class) {
         if (singleton == null) {
         singleton = new Singleton();
         }
         }
         }
         return singleton;
         }


         饿汉模式：
         [java] view plain copy
         /**
         * 懒汉式单例模式改进
         * 实现延迟加载，缓存
         * Lazy initialization holder class
         * 这个模式综合运用了java的类级内部类和多线程缺省同步锁的知识
         * @author qian.xu
         *
         *//**
        public class MySingleton2a {
            /**
             * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
             * 没有绑定的关系，而且只有被调用到才会装载，从而实现了延迟加载
             * @author qian.xu
             *
             *//**
            private static class Singleton{
                /**
                 * 静态初始化器，用JVM来保证线程安全
                 *//**
                private static MySingleton2a singleton = new MySingleton2a();

                static {
                    System.out.println("---->类级的内部类被加载");
                }
                private Singleton(){
                    System.out.println("---->类级的内部类构造函数被调用");
                }
            }
            //私有化构造函数
            private MySingleton2a(){
                System.out.println("-->开始调用构造函数");
            }
            //开放一个公有方法，判断是否已经存在实例，有返回，没有新建一个在返回
            public static MySingleton2a getInstance(){
                System.out.println("-->开始调用公有方法返回实例");
                MySingleton2a s1 = null;
                s1 = Singleton.singleton;
                System.out.println("-->返回单例");
                return s1;
            }
        }

         饿汉式:
         public class Singleton{
         private static Singleton singleton = new Singleton ();
         private Singleton (){}
         public static Singleton getInstance(){return singletion;}
         }

         懒汉式:
         public class Singleton{
         private static Singleton singleton = null;
         public static synchronized synchronized getInstance(){
         if(singleton==null){
         singleton = new Singleton();
         }
         return singleton;
         }
         }

         比较:
         饿汉式是线程安全的,在类创建的同时就已经创建好一个静态的对象供系统使用,以后不在改变
         懒汉式如果在创建实例对象时不加上synchronized则会导致对对象的访问不是线程安全的
         推荐使用第一种
         从实现方式来讲他们最大的区别就是懒汉式是延时加载,
         他是在需要的时候才创建对象,而饿汉式在虚拟机启动的时候就会创建,

         饿汉式无需关注多线程问题、写法简单明了、能用则用。但是它是加载类时创建实例（上面有个朋友写错了）、
         所以如果是一个工厂模式、缓存了很多实例、那么就得考虑效率问题，
         因为这个类一加载则把所有实例不管用不用一块创建。
         懒汉式的优点是延时加载、缺点是应该用同步（想改进的话现在还是不可能，比如double-check）、
         其实也可以不用同步、看你的需求了，多创建一两个无引用的废对象其实也没什么大不了。
         */
    }
    /**

     007.activity意外退出时信息的储存与恢复，onCreate正常进入时的判断。*/
    private void aag(){
        /**
         * 首先从这种情况下Activity的生命周期说起，当横竖屏切换时，Activity会被销毁，
         *
         * onPause,onStop,onDestory均会被调用，但是由于Activity是异常情况下终止的，
         * 所以系统会调用onSaveInstanceState方法对Activity的状态进行保存，该方法在onStop之前调用，
         * 与onPause没有既定的时序关系。当Activity被重新创建后，系统会调用onRestoreInstanceState,
         * 将之前onSaveInstanceState保存的数据Bundle传递给onRestoreInstanceState和onCreate方法，
         * 因此我们可以通过onRestoreInstanceState和onCreate方法判读Activity是否被重建了，如果被重建了，
         * 那么我们就可以取出之前保存的数据并进行恢复，onRestoreInstanceState的调用时机在onStart之后。
         * 需要注意的是：在正常情况下Activity的创建和销毁不会调用onSaveInstanceState和
         * onRestoreInstanceState方法。
         *
         * 如何防止Activity重建
         不仅仅当屏幕方向切换时会重建Activity，当系统配置发生改变的时候Activity都会被重建，例如用户插入外接键盘，运营商改变，界面模式（例如开启夜间模式）等都会导致Activity重建。如果我们不希望当系统配置发生变化界面重建，那么我们需要在AndroidManifest.xml中对Activity的configChange属性进行配置。例如我们不希望屏幕旋转时重建，则需要如下设置：

         android:configChanges="orientation"
         1
         当有多个属性时，用|进行分隔。

         虽然configChanges可以配置的选项有很多，但是我们使用比较多的有：

         orientation：屏幕方向发生了改变，例如横竖屏切换；
         locale：设备的本地位置发生了改变，例如切换了系统语言；
         keyboard：键盘类型发生了改变，例如插入了外接键盘；
         keyboardHidden：键盘的可访问性发生了改变，例如移除了外接键盘；

         注意：如果当我们的minSdkVersion和targetSdkVersion有一个大于13时，
         为了防止旋转屏幕时Activity重启，我们还需要加上screenSize。
         */
    }

    /**

     008.谈谈性能优化*/
    private void aah(){
        /**
         * 稳定（内存溢出、崩溃）
         流畅（卡顿）
         耗损（耗电、流量）
         安装包（APK瘦身）

         一、稳定——内存优化

         由于Android应用的沙箱机制，每个应用所分配的内存大小是有限度的，
         内存太低就会触发LMK——Low Memory Killer机制，就会出现闪退现象。
         先搞懂java的内存是如何分配和回收的，问题就迎刃而解了。

         相关文章：
         Java 垃圾回收器的GC机制，看这一篇就够了
         Android 内存泄漏常见案例及分析
         知道内存是如何分配和内存回收机制后，就算对内存优化有一点的认识和掌握了。
         使用分析工具
         Memory Monitor 工具：
         它是Android Studio自带的一个内存监视工具，它可以很好地帮助我们进行内存实时分析。
         通过点击Android Studio右下角的Memory Monitor标签，打开工具可以看见较浅蓝色代表free的内存，
         而深色的部分代表使用的内存从内存变换的走势图变换，可以判断关于内存的使用状态，
         例如当内存持续增高时，可能发生内存泄漏；当内存突然减少时，可能发生GC等。
         Memory Analyzer工具：
         MAT 是一个快速，功能丰富的 Java Heap 分析工具，通过分析 Java 进程的内存快照 HPROF 分析，
         从众多的对象中分析，快速计算出在内存中对象占用的大小，查看哪些对象不能被垃圾收集器回收，
         并可以通过视图直观地查看可能造成这种结果的对象。

         LeakCanary工具：
         简单，傻瓜式操作。这个工具是在Github开源的，是Square公司出品的，
         不是有一句话嘛，Square出品必属精品，https://github.com/square/leakcanary我们可以在Gradle里引用它。
         Android Lint 工具：
         是Android Sutido种集成的一个Android代码提示工具，它可以给你布局、
         代码提供非常强大的帮助。如果在布局文件中写了三层冗余的LinearLayout布局，
         就会在编辑器右边看到提示。当然这个是一个简单的举例，Lint的功能非常强大，
         大家应该养成写完代码查看Lint的习惯，这不仅让你及时发现代码种隐藏的一些问题，
         更能让你养成良好的代码风格，要知道，这些Lint提示可都是Google大牛们汗水合智慧的结晶。
         稳定性建议
         影响稳定性的原因很多，比如内存使用不合理、代码异常场景考虑不周全、代码逻辑不合理等，
         都会对应用的稳定性造成影响。其中最常见的两个场景是：Crash 和 ANR，
         这两个错误将会使得程序无法使用。所以做好Crash监控，把崩溃信息、
         异常信息收集记录起来，以便后续分析;合理使用主线程处理业务，不要在主线程中做耗时操作，
         防止ANR程序无响应发生。


         二、流畅——交互优化
         交互是与用户体验最直接的方面，交互场景大概分为四个部分：UI 绘制、应用启动、页面跳转、
         事件响应，如图：


         四个方面大致归为如下两类：
         界面绘制：主要原因是绘制的层级深、页面复杂、刷新不合理，
         由于这些原因导致卡顿的场景更多出现在 UI 和启动后的初始界面以及跳转到页面的绘制上。
         数据处理：导致这种卡顿场景的原因是数据处理量太大，一般分为三种情况，
         一是数据在处理 UI 线程，二是数据处理占用 CPU 高，导致主线程拿不到时间片，
         三是内存增加导致 GC 频繁，从而引起卡顿。


         总结原因：我们知道Android的绘制步骤是：：Measure、Layout、Draw，所以布局的层级越深、
         元素越多、耗时也就越长。还有就是Android 系统每隔 16ms 发出 VSYNC 信号，触发对
         UI 进行渲染，如果每次渲染都成功，这样就能够达到流畅的画面所需的 60FPS。
         如果某个操作花费的时间是 24ms ，系统在得到 VSYNC 信号时就无法正常进行正常渲染，
         这样就发生了丢帧现象。那么用户在 32ms 内看到的会是同一帧画面，无法在 16ms 完成渲染，
         最终引起刷新不及时。两个根本原因：


         绘制任务太重，绘制一帧内容耗时太长。

         主线程太忙，根据系统传递过来的 VSYNC 信号来时还没准备好数据导致丢帧。



         建议1：布局优化
         在Android种系统对View进行测量、布局和绘制时，都是通过对View数的遍历来进行操作的。
         如果一个View数的高度太高就会严重影响测量、布局和绘制的速度。Google也在其API文档中建
         议View高度不宜哦过10层。现在版本种Google使用RelativeLayout替代LineraLayout作为默认根布局，
         目的就是降低LineraLayout嵌套产生布局树的高度，从而提高UI渲染的效率。
         布局复用，使用<include>标签重用layout；
         提高显示速度，使用<ViewStub>延迟View加载；
         减少层级，使用<merge>标签替换父级布局；
         注意使用wrap_content，会增加measure计算成本；

         删除控件中无用属性；

         建议2：绘制优化
         过度绘制是指在屏幕上的某个像素在同一帧的时间内被绘制了多次。在多层次重叠的 UI 结构中，
         如果不可见的 UI 也在做绘制的操作，就会导致某些像素区域被绘制了多次，从而浪费了多余的
         CPU 以及 GPU 资源。所以避免过度绘制：
         布局上的优化。移除 XML 中非必须的背景，移除 Window 默认的背景、按需显示占位背景图片

         自定义View优化。使用 canvas.clipRect()来帮助系统识别那些可见的区域，只有在这个区域内才会被绘制。


         建议3：启动优化
         UI 布局。应用一般都有闪屏页，优化闪屏页的 UI 布局，可以通过 Profile GPU Rendering 检测丢帧情况。

         启动加载逻辑优化。可以采用分布加载、异步加载、延期加载策略来提高应用启动速度。

         数据准备。数据初始化分析，加载数据可以考虑用线程初始化等策略。

         建议4：刷新优化
         减少刷新次数；
         缩小刷新区域；
         建议5：动画优化
         在实现动画效果时，需要根据不同场景选择合适的动画框架来实现。
         有些情况下，可以用硬件加速方式来提供流畅度。

         三、节省——耗电优化
         在移动设备中，电池的重要性不言而喻，没有电什么都干不成。对于操作系统和设备开发商来说，

         耗电优化一致没有停止，去追求更长的待机时间，而对于一款应用来说，并不是可以忽略电量使用问题
         ，特别是那些被归为“电池杀手”的应用，最终的结果是被卸载。因此，应用开发者在实现需求的同时，
         需要尽量减少电量的消耗。

         在 Android5.0 以前，在应用中测试电量消耗比较麻烦，也不准确，5.0 之后专门引入了一
         个获取设备上电量消耗信息的 API:Battery Historian。Battery Historian 是一款由
         Google 提供的 Android 系统电量分析工具，和Systrace 一样，是一款图形化数据分析工具

         ，直观地展示出手机的电量消耗过程，通过输入电量分析文件，显示消耗情况，最后提供一些

         可供参考电量优化的方法。

         除此之外，还有一些常用方案可提供：

         计算优化，避开浮点运算等。

         避免 WaleLock 使用不当。

         使用 Job Scheduler。


         四、APK瘦身
         应用安装包大小对应用使用没有影响，但应用的安装包越大，
         用户下载的门槛越高，特别是在移动网络情况下，用户在下载应用时，
         对安装包大小的要求更高，因此，减小安装包大小可以让更多用户愿意下载和体验产品。

         常用应用安装包的构成，如图所示：







         从图中我们可以看到：



         assets文件夹。存放一些配置文件、资源文件，assets不会自动生成对应的 ID，
         而是通过 AssetManager 类的接口获取。

         res。res 是 resource 的缩写，这个目录存放资源文件，会自动生成对应的 ID 并映射到
         .R 文件中，访问直接使用资源 ID。

         META-INF。保存应用的签名信息，签名信息可以验证 APK 文件的完整性。

         AndroidManifest.xml。这个文件用来描述 Android 应用的配置信息，
         一些组件的注册信息、可使用权限等。

         classes.dex。Dalvik 字节码程序，让 Dalvik 虚拟机可执行，一般情况下
         ，Android 应用在打包时通过 Android SDK 中的 dx 工具将 Java 字节码转换为 Dalvik 字节码。

         resources.arsc。记录着资源文件和资源 ID 之间的映射关系，用来根据资源 ID 寻找资源。



         减少安装包大小的常用方案

         代码混淆。使用proGuard 代码混淆器工具，它包括压缩、优化、混淆等功能。

         资源优化。比如使用 Android Lint 删除冗余资源，资源文件最少化等。

         图片优化。比如利用 AAPT 工具对 PNG 格式的图片做压缩处理，降低图片色彩位数等。

         避免重复功能的库，使用 WebP图片格式等。

         插件化。比如功能模块放在服务器上，按需下载，可以减少安装包大小。




         小结：
         最后说一句，性能优化是一个非常具有挑战性的工作，上面列出很多分析内存、
         优化内存的方法，但是真正优化工作远不止这么简单，这里只是列举了一些入门的方法，
         而要进行完美的内存优化、内存分析绝非一日之功，需要开发者深厚的技术功底合耐心。




         */
    }
    /**
     009.JS的交互理解吗？平时工作用的多吗，项目中是怎么与Web交互的？*/
    private void aai(){
        /**
         *
         * js代码与Java代码互相调用
         * 在onpagestart()时,
         * 过滤加载的url,根据指定的字段进行相关的操作
         *一点小建议

         如果你的项目中有很多或者一定数量的JS交互，建议写一个有返回值的接口。然后通过JSON参数来进行控制。
         内部制定一个解析协议，根据JSON的数据来决定要做什么事，避免大量定义接口 ，也避免构建太多的实例消耗资源
         */
    }

    /**

     010.MVC -> MVP -> MVVM 这样变化的原因，MVP的不足，MVVM为什么代替了MVP*/
    private void aaj(){
        /**
         * MVC优缺点
         优点
         首先最重要的一点是多个视图能共享一个模型，同一个模型可以被不同的视图重用，
         大大提高了代码的可重用性。
         由于MVC的三个模块相互独立，在其中改变一个，其他两个可以保持不变，
         这样可以把耦合降得很低，这样可以使开发人员可以只关注整个系统中的某一层
         控制器提高了应用程序的灵活性和可配置型。
         控制器可以用来连接不同的模型和视图去完成用户的需求，
         这样控制器可以为构造应用程序提供强有力的手段

         缺点
         增加了系统结构的实现的复杂性
         视图与控制器之间的联系过于紧密，视图如果没有控制器的存在，
         能够做的事情少之又少，这样视图就很难独立应用了
         视图对模型的数据的访问效率过低，因为之间需要多次的调用
         View无法组件化，View依赖于特定的Model，如果需要把这个View抽出来用在下
         一个应用程序复用就比较困难了
         *
         *
         *  MVP优缺点
         MVP的优点
         便于测试。Presenter对View是通过接口进行，在对Presenter进行不依赖UI环境的单元测试的时候。
         可以通过Mock一个View对象，这个对象只需要实现了View的接口即可。
         然后依赖注入到Presenter中，单元测试的时候就可以完整的测试Presenter应用逻辑的正确性。
         这里根据上面的例子给出了Presenter的单元测试样例。
         View可以进行组件化。在MVP当中，View不依赖Model。这样就可以让View从特定的业务场景中脱离出来，
         可以说View可以做到对业务完全无知。它只需要提供一系列接口提供给上层操作。
         这样就可以做到高度可复用的View组件。
         MVP的缺点
         代码量会一些，实现的难度也会增加一些
         *
         *
         * MV-VM优缺点
         优点

         提高可维护性。解决了MVP大量的手动View和Model同步的问题，提供双向绑定机制。提高了代码的可维护性。
         简化测试。因为同步逻辑是交由Binder做的，View跟着Model同时变更，所以只需要保证Model的正确性，
         View就正确。大大减少了对View同步更新的测试。

         缺点
         过于简单的图形界面不适用，或说牛刀杀鸡。
         对于大型的图形应用程序，视图状态较多，ViewModel的构建和维护的成本都会比较高
         数据绑定的声明是指令式地写在View的模版当中的，这些内容是没办法去打断点debug的。


         * 前言
         做客户端开发、前端开发，大致都应该听说过这么几个名词MVC、MVP、MVVM，
         这些架构的思想大多是为了解决界面应用程序复杂的逻辑问题。同时这些框架的核心目的在于，
         职责分离，不同的层次要做不同的事情。

         无论是哪种MV**系列，都涉及到了Model和View，如果单纯的只有Model和View，
         他们是没有办法一起协同工作的，所以就有了各种MV..的设计模式。

         MVXX模式：
         MVC
         MVP
         MVVM

         这三种架构模式都是现在比较流行的，在不同的项目中，可能采用不同的架构模式，
         今天我们就围绕着这三种架构模式的试用场景介绍一下他们的概念，以及异同。

         MVC
         MVC(Model-View-Controller),Model:逻辑模型，View:视图模型，Controller控制器。
         简单说这就是一种设计应用程序的思想，目的在于将业务逻辑、数据、界面分离，
         将业务逻辑聚集到一个部件里面，在改进或者想要定制界面及用户交互时，
         不需要重写编写业务逻辑。Controller和View都依赖Model层，Controller和View相互依赖。
         操作的过程：用户在界面上进行操作(例如手机屏幕)，这个时候View会捕捉到用户的操作，
         然后将这个操作的处理权利交给Controller，Controller会对来自View的数据进行预处理，
         决定调用哪个Model的接口，然后由Model执行相应的逻辑，当Model变更之后，
         再利用观察者模式通知View，View通过观察者模式收到Model的消息之后，向Model请求最新的数据，
         然后更新页面，如下图：
         看似没有什么特别的地方，但是由几个需要特别关注的关键点：

         View是把控制权交移给Controller，Controller执行应用程序相关的应用逻辑
         （对来自View数据进行预处理、决定调用哪个Model的接口等等）。

         Controller操作Model，Model执行业务逻辑对数据进行处理。但不会直接操作View，
         可以说它是对View无知的。

         View和Model的同步消息是通过观察者模式进行，
         而同步操作是由View自己请求Model的数据然后对视图进行更新。

         需要特别注意的是MVC模式的精髓在于第三点：Model的更新是通过观察者模式告知View的，
         具体表现形式可以是Pub/Sub或者是触发Events。而网上很多对于MVC的描述都没有强调这一点。
         通过观察者模式的好处就是：不同的MVC三角关系可能会有共同的Model，
         一个MVC三角中的Controller操作了Model以后，两个MVC三角的View都会接受到通知，
         然后更新自己。保持了依赖同一块Model的不同View显示数据的实时性和准确性。
         我们每天都在用的观察者模式，在几十年前就已经被大神们整合到MVC的架构当中。


         MVP
         Model(Model View Presenter),Model:逻辑模型，View:视图模型，Presenter:接口。

         关于MVP的定义：
         - MVC的演化版本，让Model和View完全解耦
         - 代码清晰，不过增加了很多类
         MVP中的P，是Presenter的含义，和MVC比较类似，都是将用户对View的操作交付给Presenter，
         Presenter会执行相应的逻辑，在这过程中会操作Model，当Model执行完业务逻辑之后，
         同样是通过观察者模式把自己的结果传递出去，不过不是告诉View，而是告诉Presenter，
         Presenter得到消息后，通过View的接口更新页面。
         在Android中，我们的View可能仅仅就是个布局文件，它能做的事情少之又少，
         真正与布局文件进行数据绑定操作的事Activity，所以这样做就使Activity即像View又像Controller。

         应用了MVP之后：
         - View:对应Activity，负责View的绘制以及与用户交互
         - Model:业务逻辑和实体模型
         - Presenter:负责完成View与Model之间的交互




         MVP与MVC区别
         如下图所示：
         MVVM
         MVVM是Model-View-ViewModel的简写，MVVM是思想上的一种变革，也可以看成是MVP的一种变革，
         它是将"数据模型数据双向绑定"的思想作为核心，因此在View和Model之间便不需要我们写联系了，
         我们在修改数据的时候视图就可以发生变化，我们在修改视图的时候数据也是会发生变化的，
         可以在一定程度上提高我们的开发效率的。

         调用关系

         MVVM的调用关系和MVP一样。但是，在ViewModel当中会有一个叫Binder，
         或者是Data-binding engine的东西。以前全部由Presenter负责的View和Model之间数据
         同步操作交由给Binder处理。你只需要在View的模版语法当中，指令式地声明View上的显
         示的内容是和Model的哪一块数据绑定的。当ViewModel对进行Model更新的时候，Binder会自
         动把数据更新到View上去，当用户对View进行操作（例如表单输入），Binder也会自动
         把数据更新到Model上去。这种方式称为：Two-way data-binding，双向数据绑定。
         可以简单而不恰当地理解为一个模版引擎，但是会根据数据变更实时渲染。
         也就是说，MVVM把View和Model的同步逻辑自动化了。以前Presenter负责的View和
         Model同步不再手动地进行操作，而是交由框架所提供的Binder进行负责。只需要告诉Binder，
         View显示的数据对应的是Model哪一部分即可。

         Android官方推出的MVVM的DataBinding，便是一个双向绑定的库。

         */
    }
    /**

     011.MVC的情况下怎么把Activity的C和V抽离*/
    private void aak(){
        /**
         * MVC与android分别对应内容如下：
         　　1、模型层（model）：对数据库的操作、对网络等的操作都应该在model里面处理，
         对业务计算等操作也是必须放在的该层的。
         　　2、视图层（view）：一般采用xml文件进行界面的描述，使用的时候可以非常方便的引入，
         在android中也可以使用javascript+html等的方式作为view层，
         这里需要进行java和javascript之间的通信，android提供了它们之间非常方便的通信实现。
         　　3、控制层（controller）：android的控制层通常在acitvity，
         不要直接在acitivity中写代码，要通过activity交割model业务逻辑层处理，
         这样做的另外一个原因是android中的acitivity的响应时间是5s，如果耗时的操作放在这里，
         程序就很容易被回收掉。
         　　提示：mvc是model,view,controller的缩写，mvc包含三个部分：
         　　1、模型（model）对象：是应用程序的主体部分，所有的业务逻辑都应该写在该层。
         　　2、视图（view）对象：是应用程序中负责生成用户界面的部分。
         也是在整个mvc架构中用户唯一可以看到的一层，接收用户的输入，显示处理结果。
         　　3、控制器（control）对象：是根据用户的输入，
         控制用户界面数据显示及更新model对象状态的部分，控制器更重要的一种导航功能，想用用户出发的相关事件，交给m处理。
         */
    }

    /**
     012.各个网络框架之间的差异和优缺点，网络框架代替进化的原因*/
    private void aal(){

        /**
         * Volley
         既然在android2.2之后不建议使用Http Client，那么有没有一个库是android2.2及以下版本使用Http Client，
         而android2.3及以上版本使用HttpUrlConnection的呢，答案是肯定的，
         就是Volley,它是android开发团队在2013年Google I/O大会上推出了一个新的网络通信框架

         Volley可以说是把AsyncHttpClient和Universal-Image-Loader的优点集于了一身，
         既可以像AsyncHttpClient一样非常简单地进行HTTP通信，
         也可以像Universal-Image-Loader一样轻松加载网络上的图片。除了简单易用之外，
         Volley在性能方面也进行了大幅度的调整，它的设计目标就是非常适合去进行数据量不大，
         但通信频繁的网络操作，而对于大数据量的网络操作，比如说下载文件等，Volley的表现就会非常糟糕

         特点
         Volley的优势在于处理小文件的http请求；
         在Volley中也是可以使用Okhttp作为传输层
         Volley在处理高分辨率的图像压缩上有很好的支持；
         NetworkImageView在GC的使用模式上更加保守，在请求清理上也更加积极，
         networkimageview仅仅依赖于强大的内存引用，并当一个新请求是来自ImageView或ImageView离开屏幕时
         会清理掉所有的请求数据。

         okHttp
         okhttp 是一个 Java 的 HTTP+SPDY 客户端开发包，同时也支持 Android。需要Android 2.3以上。

         特点
         OKHttp是Android版Http客户端。非常高效，支持SPDY、连接池、GZIP和 HTTP 缓存。
         默认情况下，OKHttp会自动处理常见的网络问题，像二次连接、SSL的握手问题。
         如果你的应用程序中集成了OKHttp，Retrofit默认会使用OKHttp处理其他网络层请求。
         从Android4.4开始HttpURLConnection的底层实现采用的是okHttp.

         Retrofit
         特点
         性能最好，处理最快
         使用REST API时非常方便；
         传输层默认就使用OkHttp；
         支持NIO；
         拥有出色的API文档和社区支持
         速度上比volley更快；
         如果你的应用程序中集成了OKHttp，Retrofit默认会使用OKHttp处理其他网络层请求。
         默认使用Gson
         https://www.cnblogs.com/changyaohua/p/4992987.html
         */
    }

    /**
     013.图片缓存框架的差异和优缺点，有没有比Glide更好的图片加载框架？*/
    private void aam(){
        /**
         *为什么图片加载我首先想到Glide，图片加载框架用了不少，从afinal框架的afinalBitmap，
         * Xutils的BitmapUtils，老牌框架universalImageLoader,著名开源组织square的picasso,
         * google推荐的glide到FaceBook推出的fresco。
         * 这些我前前后后都体验过，那么面对这么多的框架，该如何选择呢？下面简单分析下我的看法。
         afinal和Xuils在github上作者已经停止维护了，开源社区最新的框架要属KJFramework，
         不过这种快速开发框架看似很好用，功能也应有尽有，小型项目也罢，大型项目我不是很推荐，
         这样做项目的耦合度太高，一旦出现停止维护，而新的问题不断增加，没人处理就麻烦了。

         在glide和fresco还未出来的时候，当时最火的莫过于universalImageLoader和picasso了，
         当时觉得universalImageLoader配置相对picasso麻烦，虽然提供了各种配置，
         但是没有实践过，根本不知道如何配置，还不如都采用默认配置
         ，就选择了picasso作为图片加载框架，用了近一年的时间，没有太大的问题，
         且使用简单，或许是因为之前的项目太过于简单，周期也并不是很长，还有使用eclipse开发，
         一个很大的问题一直都没有暴露出来，换上了最新的Android Studio可以清晰的看到各种性能相关的监控，
         如cpu还有内存监控，终于知道了之前做的项目都那么的卡顿的罪魁祸首，
         picasso加载稍微大一点的图片就特别耗内存，通常一个listView或者顶部滑动广告栏都含有多张图片，
         这使得做出的页面只要含图片较多就异常卡顿（之前的时候还把它归结为测试机不好），
         知道这一点后我就有点想把picasso给替换掉，但这一次我不能那么粗心。

         测试了picasso，glide，universalImageLoader，fresco这四个框架，测试内容大概有以下几项，
         内存测试，大图片测试，小图片测试，本地图片，网络图片当然还结合官方文档体验其特色功能，
         内存测试中，glide，universalImageLoader，fresco表现都非常优秀，picasso这一点上实在是太糟糕了，
         小图片差别也不是很大，稍微大点图片内存消耗就要比其他高出几倍，这一点上证明了我的猜想，
         picasso不能再用了，下面一项项分析其他框架，在高于2M左右大图测试中fresco的表现则和picasso
         一样直接神马都不显示，项目中要实现大图预览功能，这点上是不行的，接着看universalImageLoader
         和glide在这几项测试中成绩都很好，到底该如何选择呢？

         因为我项目之前用的picasso，glide从用法上几乎就是另一个picasso，
         从picasso转移到glide相对改动较少，还有一点就是这个项目是google在维护，
         我也能给它更多的信任，相比较universalImageLoader，glide可以支持gif和短视频，
         后期也需要用到，这里不得不谈一下glide优秀的缓存机制了，glide图片缓存默认使用RGB565相当
         于ARGB8888可以节省不少的空间，支持与activity，fragment，application生命周期的联动，
         更智能管理图片请求当然还有其他的扩展更多可以看 glide介绍 当然，glide的方法数量
         比universalImageLoader多了1000多个，遇到64k问题的会比较关注这个。

         刚才只是掠过fresco，其实我对他的期待还是蛮大的，因为刚出来还有居多不稳定的地方，里
         面存在着大量吸引着我的功能，支持webps格式（和jpg一样都是有损压缩格式，
         webps相同质量图片更节省空间），支持渐进式jpeg，可以轻松的定制image的各种属性，
         支持多图请求和图片复用，并支持手势缩放和旋转等等，更多介绍 fresco，
         当然，实际用的时候并没有那么好，很多功能都有待完善。

         还有一点细节的地方要注意的，最好不要直接拿来用，至少经过自己简单的封装，
         而不是直接在项目中使用，一个简单的例子，后期图片过多，可能需要另外配置一台机器单独存放图片，
         主机地址做成可配置，可不要因为一个简单的需求又要加班了
         */
    }
    private void aan(){
        /**
         *
         谈谈对Java多态的理解？
         多态是指父类的某个方法被子类重写时，可以产生自己的功能行为，
         同一个操作作用于不同对象，可以有不同的解释，产生不同的执行结果。

         多态的三个必要条件：

         继承父类。
         重写父类的方法。
         父类的引用指向子类对象。
         静态方法与静态成员变量可以被继承吗，为什么？
         静态方法与静态成员变量可以被继承，但是不能被重写。
         它对子类隐藏，因此静态方法也不能实现多态。

         为什么Java里的匿名内部类只能访问final修饰的外部变量？
         匿名内部类用法

         public class TryUsingAnonymousClass {
         public void useMyInterface() {
         final Integer number = 123;
         System.out.println(number);

         MyInterface myInterface = new MyInterface() {
        @Override
        public void doSomething() {
        System.out.println(number);
        }
        };
         myInterface.doSomething();

         System.out.println(number);
         }
         }
         编译后的结果

         class TryUsingAnonymousClass$1
         implements MyInterface {
         private final TryUsingAnonymousClass this$0;
         private final Integer paramInteger;

         TryUsingAnonymousClass$1(TryUsingAnonymousClass this$0, Integer paramInteger) {
         this.this$0 = this$0;
         this.paramInteger = paramInteger;
         }

         public void doSomething() {
         System.out.println(this.paramInteger);
         }
         }
         因为匿名内部类最终用会编译成一个单独的类，
         而被该类使用的变量会以构造函数参数的形式传递给该类，
         例如：Integer paramInteger，如果变量 不定义成final的，
         paramInteger在匿名内部类被可以被修改，进而造成和外部的paramInteger不
         一致的问题，为了避免这种不一致的情况，因为Java 规定匿名内部类只能访问final修饰的外部变量。

         讲一下Java的编码方式？
         为什么需要编码

         计算机存储信息的最小单元是一个字节即8bit，
         所以能表示的范围是0~255，这个范围无法保存所有的字符，
         所以需要一个新的数据结构char来表示这些字符，从char到byte需要编码。

         常见的编码方式有以下几种：

         ASCII：总共有 128 个，用一个字节的低 7 位表示，031 是控制字符如换行回车删除等；
         32126 是打印字符，可以通过键盘输入并且能够显示出来。
         GBK：码范围是 8140~FEFE（去掉 XX7F）总共有 23940 个码位，
         它能表示 21003 个汉字，它的编码是和 GB2312 兼容的，也就是说用 GB2312
         编码的汉字可以用 GBK 来解码，并且不会有乱码。
         UTF-16：UTF-16 具体定义了 Unicode 字符在计算机中存取方法。UTF-16
         用两个字节来表示 Unicode 转化格式，这个是定长的表示方法，
         不论什么字符都可以用两个字节表示，两个字节是 16 个 bit，所以叫
         UTF-16。UTF-16 表示字符非常方便，每两个字节表示一个字符，这个在字符
         串操作时就大大简化了操作，这也是 Java 以 UTF-16 作为内存的字符存储格式的一个很重要的原因。
         UTF-8：统一采用两个字节表示一个字符，虽然在表示上非常简单方便，但是也有其缺点，
         有很大一部分字符用一个字节就可以表示的现在要两个字节表示，存储空间放大了一倍，
         在现在的网络带宽还非常有限的今天，这样会增大网络传输的流量，而且也没必要。
         而 UTF-8 采用了一种变长技术，每个编码区域有不同的字码长度。
         不同类型的字符可以是由 1~6 个字节组成。
         Java中需要编码的地方一般都在字符到字节的转换上，这个一般包括磁盘IO和网络IO。

         Reader 类是 Java 的 I/O 中读字符的父类，而 InputStream 类是读字节的父类，I
         nputStreamReader 类就是关联字节到字符的桥梁，它负责在 I/O 过程中处
         读取字节到字符的转换，而具体字节到字符的解码实现它由 StreamDecoder

         去实现，在 StreamDecoder 解码过程中必须由用户指定 Charset 编码格式。

         静态代理与动态代理区别是什么，分别用在什么样的场景里？
         静态代理与动态代理的区别在于代理类生成的时间不同，如果需要对多个类进行代理，
         并且代理的功能都是一样的，用静态代理重复编写代理类就非常的麻烦，
         可以用静态代理动态的生成代理类。

         // 为目标对象生成代理对象
         public Object getProxyInstance() {
         return Proxy.newProxyInstance(target.getClass().getClassLoader(),
         target.getClass().getInterfaces(),
         new InvocationHandler() {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开启事务");

        // 执行目标对象方法
        Object returnValue = method.invoke(target, args);

        System.out.println("提交事务");
        return null;
        }
        });
         }
         描述一下Java的异常体系？
         Error是程序无法处理的错误，比如OutOfMemoryError、ThreadDeath等。
         这些异常发生时， Java虚拟机（JVM）一般会选择线程终止。
         Exception是程序本身可以处理的异常，这种异常分两大类运行时异常和非运行时异常，
         程序中应当尽可能去处理这些异常。运行时异常都是RuntimeException类及其子类异常，
         如NullPointerException、IndexOutOfBoundsException等， 这些异常是不检查异常，
         程序中可以选择捕获处理，也可以不处理。这些异常一般是由程序逻辑错误引起的，
         程序应该从逻辑角度尽可能避免这类异常的发生。
         描述一个类的加载过程？
         Person person = new Person()

         查找Person.class，并加载到内存中。·
         执行类里的静态代码块。
         在堆内存里开辟内存空间，并分配内存地址。
         在堆内存里建立对象的属性，并进行默认的初始化。
         对属性就行显示初始化。
         对对象进行构造代码块初始化。
         调用对象的构造函数进行初始化。
         将对象的地址赋值给person变量。
         Java对象的生命周期是什么？
         加载：将类的信息加载到JVM的方法区，然后在堆区中实例化一个java.lang.Class对象，
         作为方法去中这个类的信息入口。
         连接：验证：验证类是否合法。
         准备：为静态变量分配内存并设置JVM默认值，非静态变量不会分配内存。
         解析：将常量池里的符号引用转换为直接引用。
         初始化：初始化类的静态赋值语句和静态代码块，主动引用会被触发类的初始化，
         被动引用不会触发类的初始化。
         使用：执行类的初始化，主动引用会被触发类的初始化，
         被动引用不会触发类的初始化。
         卸载：卸载过程就是清楚堆里类的信息，以下情况会被卸载：
         ① 类的所有实例都已经被回收。② 类的ClassLoader被回收。
         ③ 类的CLass对象没有被任何地方引用，无法在任何地方通过 反射访问该类。
         描述一下类的加载机制？
         类的加载就是虚拟机通过一个类的全限定名来获取描述此类的二进制字节流，
         而完成这个加载动作的就是类加载器。

         类和类加载器息息相关，判定两个类是否相等，只有在这两个类被同一
         个类加载器加载的情况下才有意义，否则即便是两个类来自同一个Class文件，
         被不同类加载器加载，它们也是不相等的。

         注：这里的相等性保函Class对象的equals()方法、isAssignableFrom()方法、is
         Instance()方法的返回结果以及Instance关键字对对象所属关系的判定结果等。

         类加载器可以分为三类：

         启动类加载器（Bootstrap ClassLoader）：负责加载<JAVA_HOME>\lib目录下或者被-Xbootclasspath
         参数所指定的路径的，并且是被虚拟机所识别的库到内存中。
         扩展类加载器（Extension ClassLoader）：负责加载<JAVA_HOME>\lib\ext目录下或者
         被java.ext.dirs系统变量所指定的路径的所有类库到内存中。
         应用类加载器（Application ClassLoader）：负责加载用户类路径上的指定类库，
         如果应用程序中没有实现自己的类加载器，一般就是这个类加载器去加载应用程序中的类库。
         这么多类加载器，那么当类在加载的时候会使用哪个加载器呢？thinking

         这个时候就要提到类加载器的双亲委派模型，流程图如下所示：



         双亲委派模型的整个工作流程非常的简单，如下所示：

         如果一个类加载器收到了加载类的请求，它不会自己立即去加载类，
         它会先去请求父类加载器，每个层次的类加载器都是如此。层层传递，
         直到传递到最高层的类加载器，只有当 父类加载器反馈自己无法加载这个类，
         才会有当前子类加载器去加载该类。

         关于双亲委派机制，在ClassLoader源码里也可以看出，如下所示：

         public abstract class ClassLoader {

         protected Class<?> loadClass(String name, boolean resolve)
         throws ClassNotFoundException
         {
         //首先，检查该类是否已经被加载
         Class c = findLoadedClass(name);
         if (c == null) {
         long t0 = System.nanoTime();
         try {
         //先调用父类加载器去加载
         if (parent != null) {
         c = parent.loadClass(name, false);
         } else {
         c = findBootstrapClassOrNull(name);
         }
         } catch (ClassNotFoundException e) {
         // ClassNotFoundException thrown if class not found
         // from the non-null parent class loader
         }

         if (c == null) {
         //如果父类加载器没有加载到该类，则自己去执行加载
         long t1 = System.nanoTime();
         c = findClass(name);

         // this is the defining class loader; record the stats
         }
         }
         return c;
         }
         }
         为什么要这么做呢？thinking

         这是为了要让越基础的类由越高层的类加载器加载，例如Object类，
         无论哪个类加载器去尝试加载这个类，最终都会传递给最高层的类加载器去加载，
         前面我们也说过，类的相等性是由 类与其类加载器共同判定的，
         这样Object类无论在何种类加载器环境下都是同一个类。

         相反如果没有双亲委派模型，那么每个类加载器都会去加载Object，
         那么系统中就会出现多个不同的Object类了，如此一来系统的最基础的行为也就无法保证了。

         描述一下GC的原理和回收策略？
         提到垃圾回收，我们可以先思考一下，如果我们去做垃圾回收需要解决哪些问题？ thinking

         一般说来，我们要解决一些三个问题：

         哪些内存回收？
         什么时候回收？
         如何回收？
         这些问题分别对应着引用管理和回收策略等方案。

         提到引用，我们都知道Java中有四种引用类型：

         强引用：代码中普遍存在的，只要强引用还存在，垃圾收集器就不会回收掉被引用的对象。
         软引用：SoftReference，用来描述还有用但是非必须的对象，
         当内存不足的时候回回收这类对象。
         弱引用：WeakReference，用来描述非必须对象，
         弱引用的对象只能生存到下一次GC发生时，当GC发生时，无论内存是否足够，都会回收该对象。
         虚引用：PhantomReference，一个对象是否有虚引用的存在，
         完全不会对其生存时间产生影响，也无法通过虚引用取得一个对象的引用，
         它存在的唯一目的是在这个对象被回收时可以收到一个系统通知。
         不同的引用类型，在做GC时会区别对待，我们平时生成的Java对象，
         默认都是强引用，也就是说只要强引用还在，GC就不会回收，那么如何判断强引用是否存在呢？thinking

         一个简单的思路就是：引用计数法，有对这个对象的引用就+1，不再引用就-1
         ，但是这种方式看起来简单美好，但它却不嫩解决循环引用计数的问题。

         因此可达性分析算法登上历史舞台sunglasses，用它来判断对象的引用是否存在。

         可达性分析算法通过一系列称为GC Roots的对象作为起始点，
         从这些节点从上向下搜索，搜索走过的路径称为引用链，
         当一个对象没有任何引用链 与GC Roots连接时就说明此对象不可用，也就是对象不可达。

         GC Roots对象通常包括：

         虚拟机栈中引用的对象（栈帧中的本地变量表）
         方法去中类的静态属性引用的对象
         方法区中常量引用的对象
         Native方法引用的对象
         可达性分析算法整个流程如下所示：

         第一次标记：对象在经过可达性分析后发现没有与GC Roots有引用链，

         则进行第一次标记并进行一次筛选，筛选条件是：该对象是否有必要执行finalize()方法。
         没有覆盖finalize()方法或者finalize()方法已经被执行过都会被 认为没有必要执行。
         如果有必要执行：则该对象会被放在一个F-Queue队列，并稍后在由虚拟机建立的低优先
         级Finalizer线程中触发该对象的finalize()方法，但不保证一定等待它执行结束，
         因为如果这个对象的finalize()方法发生了死循环或者执行 时间较长的情况，
         会阻塞F-Queue队列里的其他对象，影响GC。
         第二次标记：GC对F-Queue队列里的对象进行第二次标记，如果在第二次标记时该对象
         又成功被引用，则会被移除即将回收的集合，否则会被回收。
         接口和抽象类有什么区别？
         共同点

         是上层的抽象层。
         都不能被实例化
         都能包含抽象的方法，这些抽象的方法用于描述类具备的功能，但是不比提供具体的实现。
         区别

         在抽象类中可以写非抽象的方法，从而避免在子类中重复书写他们，这样可以提高代码的复用性，
         这是抽象类的优势；接口中只能有抽象的方法。
         一个类只能继承一个直接父类，这个父类可以是具体的类也可是抽象类；但是一个类可以实现多个接口。
         内部类、静态内部类在业务中的应用场景是什么？
         静态内部类：只是为了降低包的深度，方便类的使用，静态内部类适用于包含类当中，
         但又不依赖与外在的类，不用使用外在类的非静态属性和方法，只是为了方便管理类结构而定义。
         在创建静态内部类的时候，不需要外部类对象的引用。
         非静态内部类：持有外部类的引用，可以自由使用外部类的所有变量和方法
         synchronized与ReentrantLock有什么区别？
         synchronized是互斥同步的一种实现。

         synchronized：当某个线程访问被synchronized标记的方法或代码块时，
         这个线程便获得了该对象的锁，其他线程暂时无法访问这个方法，
         只有等待这个方法执行完毕或者代码块执行完毕，这个 线程才会释放该对象的锁，
         其他线程才能执行这个方法或代码块。

         前面我们已经说了volatile关键字，这里我们举个例子来综合分析volatile
         与synchronized关键字的使用。

         point_up举个栗子

         public class Singleton {

         //volatile保证了：1 instance在多线程并发的可见性 2 禁止instance在操作是的指令重排序
         private volatile static Singleton instance;

         public static Singleton getInstance() {
         //第一次判空，保证不必要的同步
         if (instance == null) {
         //synchronized对Singleton加全局所，保证每次只要一个线程创建实例
         synchronized (Singleton.class) {
         //第二次判空时为了在null的情况下创建实例
         if (instance == null) {
         instance = new Singleton();
         }
         }
         }
         return instance;
         }
         }
         这是一个经典的DCL单例。

         它的字节码如下：



         可以看到被synchronized同步的代码块，会在前后分别加上monitorenter和monitorexit，
         这两个字节码都需要指定加锁和解锁的对象。

         关于加锁和解锁的对象：

         synchronized代码块 ：同步代码块，作用范围是整个代码块，作用对象是调用这个代码块的对象。
         synchronized方法 ：同步方法，作用范围是整个方法，作用对象是调用这个方法的对象。
         synchronized静态方法 ：同步静态方法，作用范围是整个静态方法，作用对象是调用这个类的所有对象。
         synchronized(this)：作用范围是该对象中所有被synchronized标记的变量、
         方法或代码块，作用对象是对象本身。
         synchronized(ClassName.class) ：作用范围是静态的方法或者静态变量，作用对象是Class对象。
         synchronized(this)添加的是对象锁，synchronized(ClassName.class)添加的是类锁，
         它们的区别如下：

         对象锁：Java的所有对象都含有1个互斥锁，这个锁由JVM自动获取和释放。
         线程进入synchronized方法的时候获取该对象的锁，
         当然如果已经有线程获取了这个对象的锁，那么当前线 程会等待；
         synchronized方法正常返回或者抛异常而终止，JVM会自动释放对象锁。
         这里也体现了用synchronized来加锁的好处，方法抛异常的时候，锁仍然可以由JVM来自动释放。

         类锁：对象锁是用来控制实例方法之间的同步，类锁是用来控制静态方法（
         或静态变量互斥体）之间的同步。其实类锁只是一个概念上的东西，并不是真实存在的，
         它只是用来帮助我们理 解锁定实例方法和静态方法的区别的。
         我们都知道，java类可能会有很多个对象，但是只有1个Class对象，
         也就是说类的不同实例之间共享该类的Class对象。Class对象其实也仅仅是1个
         java对象，只不过有点特殊而已。由于每个java对象都有1个互斥锁，
         而类的静态方法是需要Class对象。所以所谓的类锁，不过是Class对象的锁而已。
         获取类的Class对象有好几种，最简 单的就是MyClass.class的方式。
         类锁和对象锁不是同一个东西，一个是类的Class对象的锁，一个
         是类的实例的锁。也就是说：一个线程访问静态synchronized的时候，
         允许另一个线程访 问对象的实例synchronized方法。反过来也是成立的，因为他们需要的锁是不同的。

         volatile的原理是什么？
         volatile也是互斥同步的一种实现，不过它非常的轻量级。

         volatile有两条关键的语义：

         保证被volatile修饰的变量对所有线程都是可见的
         禁止进行指令重排序
         要理解volatile关键字，我们得先从Java的线程模型开始说起。如图所示：



         Java内存模型规定了所有字段（这些字段包括实例字段、静态字段等，
         不包括局部变量、方法参数等，因为这些是线程私有的，并不存在竞争）
         都存在主内存中，每个线程会 有自己的工作内存，工作内存里保存了线程
         所使用到的变量在主内存里的副本拷贝，线程对变量的操作只能在工作内存里进行，
         而不能直接读写主内存，当然不同内存之间也 无法直接访问对方的工作内存，
         也就是说主内存是线程传值的媒介。

         我们来理解第一句话：

         保证被volatile修饰的变量对所有线程都是可见的

         如何保证可见性？thinking

         被volatile修饰的变量在工作内存修改后会被强制写回主内存，
         其他线程在使用时也会强制从主内存刷新，这样就保证了一致性。

         关于“保证被volatile修饰的变量对所有线程都是可见的”，有种常见的错误理解：

         错误理解：由于volatile修饰的变量在各个线程里都是一致的，
         所以基于volatile变量的运算在多线程并发的情况下是安全的。

         这句话的前半部分是对的，后半部分却错了，因此它忘记考虑变量的操作是否具有原子性这一问题。

         point_up举个栗子

         private volatile int start = 0;

         private void volatileKeyword() {

         Runnable runnable = new Runnable() {
        @Override
        public void run() {
        for (int i = 0; i < 10; i++) {
        start++;
        }
        }
        };

         for (int i = 0; i < 10; i++) {
         Thread thread = new Thread(runnable);
         thread.start();
         }
         Log.d(TAG, "start = " + start);
         }


         这段代码启动了10个线程，每次10次自增，按道理最终结果应该是100，但是结果并非如此。

         为什么会这样？thinking

         仔细看一下start++，它其实并非一个原子操作，简单来看，它有两步：

         取出start的值，因为有volatile的修饰，这时候的值是正确的。
         自增，但是自增的时候，别的线程可能已经把start加大了，这种情况下就有可能把较小的
         start写回主内存中。
         所以volatile只能保证可见性，在不符合以下场景下我们依然需要通过加锁来保证原子性：

         运算结果并不依赖变量当前的值，或者只有单一线程修改变量的值。
         （要么结果不依赖当前值，要么操作是原子性的，要么只要一个线程修改变量的值）
         变量不需要与其他状态变量共同参与不变约束
         比方说我们会在线程里加个boolean变量，来判断线程是否停止，这种情况就非常适合使用volatile。

         我们再来理解第二句话。

         禁止进行指令重排序
         什么是指令重排序？thinking

         指令重排序是值指令乱序执行，即在条件允许的情况下，

         直接运行当前有能力立即执行的后续指令，避开为获取下一条指令所需数据而造成的等待，
         通过乱序执行的技术，提供执行效率。

         指令重排序绘制被volatile修饰的变量的赋值操作前，添加一个内存屏障，
         指令重排序时不能把后面的指令重排序的内存屏障之前的位置。

         关于指令重排序不是本篇文章重点讨论的内容，更多细节可以参考指令重排序。

         如何防止反射、序列化攻击单例？
         枚举单例

         public enum Singleton {
         INSTANCE {

        @Override
        protected void read() {
        System.out.println("read");
        }

        @Override
        protected void write() {
        System.out.println("write");
        }

        };
         protected abstract void read();
         protected abstract void write();
         }
         class文件：

         public abstract class Singleton extends Enum
         {

         private Singleton(String s, int i)
         {
         super(s, i);
         }

         protected abstract void read();

         protected abstract void write();

         public static Singleton[] values()
         {
         Singleton asingleton[];
         int i;
         Singleton asingleton1[];
         System.arraycopy(asingleton = ENUM$VALUES, 0, asingleton1 = new Singleton[i = asingleton.length], 0, i);
         return asingleton1;
         }

         public static Singleton valueOf(String s)
         {
         return (Singleton)Enum.valueOf(singleton/Singleton, s);
         }

         Singleton(String s, int i, Singleton singleton)
         {
         this(s, i);
         }

         public static final Singleton INSTANCE;
         private static final Singleton ENUM$VALUES[];

         static
         {
         INSTANCE = new Singleton("INSTANCE", 0) {

         protected void read()
         {
         System.out.println("read");
         }

         protected void write()
         {
         System.out.println("write");
         }

         };
         ENUM$VALUES = (new Singleton[] {
         INSTANCE
         });
         }
         }
         类的修饰abstract，所以没法实例化，反射也无能为力。
         关于线程安全的保证，其实是通过类加载机制来保证的，我们看看INSTANCE的实例化时机，是在static块中，JVM加载类的过程显然是线程安全的。
         对于防止反序列化生成新实例的问题还不是很明白，一般的方法我们会在该类中添加上如下方法，不过枚举中也没有显示的写明该方法。
         //readResolve to prevent another instance of Singleton
         private Object readResolve(){
         return INSTANCE;
         }
         线程为什么阻塞，为和要使用多线程？
         使用多线程更多的是为了提高CPU的并发，可以让CPU同事处理多个事情，多线程场景的使用场景：

         为了不让耗时操作阻塞主线程，开启新线程执行耗时操作。
         某种任务虽然耗时但是不消耗CPU，例如：磁盘IO，可以开启新线程来做，可以显著的提高效率。
         优先级比较低的任务，但是需要经常去做，例如：GC，可以开启新线程来做。
         了解线程的生命周期吗，描述一下？
         线程状态流程图图



         NEW：创建状态，线程创建之后，但是还未启动。
         RUNNABLE：运行状态，处于运行状态的线程，但有可能处于等待状态，例如等待CPU、IO等。
         WAITING：等待状态，一般是调用了wait()、join()、LockSupport.spark()等方法。
         TIMED_WAITING：超时等待状态，也就是带时间的等待状态。一般是调用了wait(time)、join(time)、LockSupport.sparkNanos()、LockSupport.sparkUnit()等方法。
         BLOCKED：阻塞状态，等待锁的释放，例如调用了synchronized增加了锁。
         TERMINATED：终止状态，一般是线程完成任务后退出或者异常终止。
         NEW、WAITING、TIMED_WAITING都比较好理解，我们重点说一说RUNNABLE运行态和BLOCKED阻塞态。

         线程进入RUNNABLE运行态一般分为五种情况：

         线程调用sleep(time)后查出了休眠时间
         线程调用的阻塞IO已经返回，阻塞方法执行完毕
         线程成功的获取了资源锁
         线程正在等待某个通知，成功的获得了其他线程发出的通知
         线程处于挂起状态，然后调用了resume()恢复方法，解除了挂起。
         线程进入BLOCKED阻塞态一般也分为五种情况：

         线程调用sleep()方法主动放弃占有的资源
         线程调用了阻塞式IO的方法，在该方法返回前，该线程被阻塞。
         线程视图获得一个资源锁，但是该资源锁正被其他线程锁持有。
         线程正在等待某个通知
         线程调度器调用suspend()方法将该线程挂起
         我们再来看看和线程状态相关的一些方法。

         sleep()方法让当前正在执行的线程在指定时间内暂停执行，正在执行的线程可以通过Thread.currentThread()方法获取。
         yield()方法放弃线程持有的CPU资源，将其让给其他任务去占用CPU执行时间。但放弃的时间不确定，有可能刚刚放弃，马上又获得CPU时间片。
         wait()方法是当前执行代码的线程进行等待，将当前线程放入预执行队列，并在wait()所在的代码处停止执行，知道接到通知或者被中断为止。该方法可以使得调用该方法的线程释放共享资源的锁， 然后从运行状态退出，进入等待队列，直到再次被唤醒。该方法只能在同步代码块里调用，否则会抛出IllegalMonitorStateException异常。
         wait(long millis)方法等待某一段时间内是否有线程对锁进行唤醒，如果超过了这个时间则自动唤醒。
         notify()方法用来通知那些可能等待该对象的对象锁的其他线程，该方法可以随机唤醒等待队列中等同一共享资源的一个线程，并使该线程退出等待队列，进入可运行状态。
         notifyAll()方法可以是所有正在等待队列中等待同一共享资源的全部线程从等待状态退出，进入可运行状态，一般会是优先级高的线程先执行，但是根据虚拟机的实现不同，也有可能是随机执行。
         join()方法可以让调用它的线程正常执行完成后，再去执行该线程后面的代码，它具有让线程排队的作用。
         线程池了解吗，有几种线程池，应用场景是什么？
         Executors类提供了一系列工厂方法用来创建线程池。这些线程是适用于不同的场景。

         newCachedThreadPool()：无界可自动回收线程池，查看线程池中有没有以前建立的线程，如果有则复用，如果没有则建立一个新的线程加入池中，池中的线程超过60s不活动则自动终止。适用于生命 周期比较短的异步任务。
         newFixedThreadPool(int nThreads)：固定大小线程池，与newCachedThreadPool()类似，但是池中持有固定数目的线程，不能随时创建线程，如果创建新线程时，超过了固定 线程数，则放在队列里等待，直到池中的某个线程被移除时，才加入池中。适用于很稳定、很正规的并发线程，多用于服务器。
         newScheduledThreadPool(int corePoolSize)：周期任务线程池，该线程池的线程可以按照delay依次执行线程，也可以周期执行。
         newSingleThreadExecutor()：单例线程池，任意时间内池中只有一个线程。
         ThreadLocal的原理了解吗？
         ThreadLocal是一个关于创建线程局部变量的类。使用场景如下所示：

         实现单个线程单例以及单个线程上下文信息存储，比如交易id等。
         实现线程安全，非线程安全的对象使用ThreadLocal之后就会变得线程安全，因为每个线程都会有一个对应的实例。
         承载一些线程相关的数据，避免在方法中来回传递参数。
         wait和notify机制，手写一下生产者和消费者模型？
         生成者消费者模型

         生产者和消费者在同一时间段内共用同一个存储空间，生产者往存储空间中添加产品，消费者从存储空间中取走产品，当存储空间为空时，消费者阻塞，当存储空间满时，生产者阻塞。

         wait()和notify()方法的实现生成者消费者模型，缓冲区满和为空时都调用wait()方法等待，当生产者生产了一个产品或者消费者消费了一个产品之后会唤醒所有线程。

         public class ProducerAndCustomerModel {

         private static Integer count = 0;
         private static final Integer FULL = 10;
         private static String LOCK = "lock";

         public static void main(String[] args) {
         Test1 test1 = new Test1();
         new Thread(test1.new Producer()).start();
         new Thread(test1.new Consumer()).start();
         new Thread(test1.new Producer()).start();
         new Thread(test1.new Consumer()).start();
         new Thread(test1.new Producer()).start();
         new Thread(test1.new Consumer()).start();
         new Thread(test1.new Producer()).start();
         new Thread(test1.new Consumer()).start();
         }
         class Producer implements Runnable {
        @Override
        public void run() {
        for (int i = 0; i < 10; i++) {
        try {
        Thread.sleep(3000);
        } catch (Exception e) {
        e.printStackTrace();
        }
        synchronized (LOCK) {
        while (count == FULL) {
        try {
        LOCK.wait();
        } catch (Exception e) {
        e.printStackTrace();
        }
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
        LOCK.notifyAll();
        }
        }
        }
        }
         class Consumer implements Runnable {
        @Override
        public void run() {
        for (int i = 0; i < 10; i++) {
        try {
        Thread.sleep(3000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        synchronized (LOCK) {
        while (count == 0) {
        try {
        LOCK.wait();
        } catch (Exception e) {
        }
        }
        count--;
        System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
        LOCK.notifyAll();
        }
        }
        }
        }
         }
         死锁是如何发生的，如何避免死锁？
         当线程A持有独占锁a，并尝试去获取独占锁b的同时，线程B持有独占锁b，并尝试获取独占锁a的情况下，就会发生AB两个线程由于互相持有对方需要的锁，而发生的阻塞现象，我们称为死锁。

         public class DeadLockDemo {

         public static void main(String[] args) {
         // 线程a
         Thread td1 = new Thread(new Runnable() {
         public void run() {
         DeadLockDemo.method1();
         }
         });
         // 线程b
         Thread td2 = new Thread(new Runnable() {
         public void run() {
         DeadLockDemo.method2();
         }
         });

         td1.start();
         td2.start();
         }

         public static void method1() {
         synchronized (String.class) {
         try {
         Thread.sleep(2000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         System.out.println("线程a尝试获取integer.class");
         synchronized (Integer.class) {

         }
         }
         }

         public static void method2() {
         synchronized (Integer.class) {
         try {
         Thread.sleep(2000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         System.out.println("线程b尝试获取String.class");
         synchronized (String.class) {

         }

         }
         }
         }
         造成死锁的四个条件：

         互斥条件：一个资源每次只能被一个线程使用。
         请求与保持条件：一个线程因请求资源而阻塞时，对已获得的资源保持不放。
         不剥夺条件：线程已获得的资源，在未使用完之前，不能强行剥夺。
         循环等待条件：若干线程之间形成一种头尾相接的循环等待资源关系。
         在并发程序中，避免了逻辑中出现复数个线程互相持有对方线程所需要的独占锁的的情况，就可以避免死锁，如下所示：

         public class BreakDeadLockDemo {

         public static void main(String[] args) {
         // 线程a
         Thread td1 = new Thread(new Runnable() {
         public void run() {
         DeadLockDemo2.method1();
         }
         });
         // 线程b
         Thread td2 = new Thread(new Runnable() {
         public void run() {
         DeadLockDemo2.method2();
         }
         });

         td1.start();
         td2.start();
         }

         public static void method1() {
         synchronized (String.class) {
         try {
         Thread.sleep(2000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         System.out.println("线程a尝试获取integer.class");
         synchronized (Integer.class) {
         System.out.println("线程a获取到integer.class");
         }

         }
         }

         public static void method2() {
         // 不再获取线程a需要的Integer.class锁。
         synchronized (String.class) {
         try {
         Thread.sleep(2000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         System.out.println("线程b尝试获取Integer.class");
         synchronized (Integer.class) {
         System.out.println("线程b获取到Integer.class");
         }

         }
         }
         }
         了解Java注解的原理吗，注解如何获取？
         注解相当于一种标记，在程序中加了注解就等于为程序打上了某种标记。程序可以利用ava的反射机制来了解你的类及各种元素上有无何种标记，针对不同的标记，就去做相 应的事件。标记可以加在包，类，字段，方法，方法的参数以及局部变量上。

         String为什么要设计成不可变，StringBuffer与StringBuilder有什么区别？
         String是不可变的（修改String时，不会在原有的内存地址修改，而是重新指向一个新对象），String用final修饰，不可继承，String本质上是个final的char[]数组，所以char[]数组的内存地址不会被修改，而且String 也没有对外暴露修改char[]数组的方法。不可变性可以保证线程安全以及字符串串常量池的实现。
         StringBuffer是线程安全的。
         StringBuilder是非线程安全的。
         Java里的幂等性了解吗？
         幂等性原本是数学上的一个概念，即：f(x) = f(f(x))，对同一个系统，使用同样的条件，一次请求和重复的多次请求对系统资源的影响是一致的。

         幂等性最为常见的应用就是电商的客户付款，试想一下，如果你在付款的时候因为网络等各种问题失败了，然后又去重复的付了一次，是一种多么糟糕的体验。幂等性 就是为了解决这样的问题。

         实现幂等性可可以使用Token机制。

         核心思想是为每一次操作生成一个唯一性的凭证，也就是token。一个token在操作的每一个阶段只有一次执行权，一旦执行成功则保存执行结果。对 重复的请求，返回同一个结果。

         例如：电商平台上的订单id就是最适合的token。当用户下单时，会经历多个环节，比如生成订单，减库存，减优惠券等等。每一个环节执行时都先检 测一下该订单id是否已经执行过这一步骤，对未执行的请求，执行操作并缓存结果，而对已经执行过的id，则直接返回之前的执行结果，不做任何操

         作。这样可以在最大程度上避免操作的重复执行问题，缓存起来的执行结果也能用于事务的控制等。

         Java泛型了解吗，知道它的运行机制吗？
         泛型是为了参数化类型。

         为什么使用泛型？

         相对于使用Object这种简单粗暴的方式，泛型提供了一种参数化的能力，使得数据的类型可以像参数一样被传递进来，这提供了一种扩展能力。
         当数据类型确定以后，提供了一种类型检测机制，只有相匹配的数据才可以正常赋值，否则编译错误，增强了安全性。
         泛型提高了代码的可读性，不必等到运行时采取执行类型转换，在编写代码阶段，程序员就可以通过参数书写正确的数据类型。
         除了用 表示泛型外，还有 <?> 这种形式。？ 被称为通配符。

         被称作无限定的通配符。
         被称作有上限的通配符。
         被称作有下限的通配符。
         Java里的反射为何会消耗性能？
         反射慢主要因为反射是动态类型，这样导致把在zhuang

         Java的类型擦除，知道它的原理吗？
         泛型信息只存在代码编译阶段，在进入JVM之前，与泛型相关的信息都会被擦除掉。

         在类型擦除的时候，如果泛型类里的类型参数没有指定上限，例如：，则会被转成Object类型，如果指定了上限，例如：，则会 被传换成对应的类型上限。

         闭包了解吗，Java里有闭包吗？
         「函数」和「函数内部能访问到的变量」（也叫环境）的总和，就是一个闭包。

         fun main(args: Array<String>) {
         test
         }
         val test = if (5 > 3) {
         print("yes")
         } else {
         print("no")
         }
         Lambda表达式了解吗？
         Lambda 表达式俗称匿名函数。Kotlin 的 Lambda表达式更“纯粹”一点， 因为它是真正把Lambda抽象为了一种类型，而 Java 8 的 Lambda 只是单方法匿名接口实现的语法糖罢了。

         val printMsg = { msg: String ->
         println(msg)
         }

         fun main(args: Array<String>) {
         printMsg("hello")
         }
         高阶函数了解吗？
         当定义一个闭包作为参数的函数，称这个函数为高阶函数。

         fun main(args: Array<String>) {
         log("world", printMsg)
         }

         val printMsg = { str: String ->
         println(str)
         }

         val log = { str: String, printLog: (String) -> Unit ->
         printLog(str)
         }
         */
    }

    private void aao(){
        /**

         项目框架里有没有Base类，BaseActivity和BaseFragment这种封装导致的问题，以及解决方法

         框架里是怎样实现MVC的

         Reftofit用过没有，注解实现的好处？

         项目中的的界面既然是基于View的，有没有动画的处理？

         为什么不推荐软引用，软引用在dvm上的垃圾回收机制和jvm上一样吗？

         LRUCache的删除条件，LRU是什么意思(最近最少使用算法)

         启动页缓存设计 白屏问题(manifest中主题样式        <item name="android:windowBackground">@null</item>
         )

         网络图片怎么加载？Glide如何确定图片加载完毕

         项目框架中对多View的支持？

         Http的request和response的协议组成

         RecyclerView和ListView相比有哪些好处，为什么ListView被RecyclerView代替？

         公司B
         Service的源码

         Handler的实现，Looper怎么终止。

         项目是MVC，那根据自己负责的项目讲下Model、View、Controller层

         问了下昼夜模式、多语种、屏幕适配的问题，追问了下，
         如果要关闭昼夜模式功能怎么办？很多类的话，一个个去关吗？

         有没有接触过JNI和NDK？

         ListView的错位问题原因以及如何处理？

         如何设计一个抽奖系统，比如满200抽20，满500抽50
         */
    }



}
