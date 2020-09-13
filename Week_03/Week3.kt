package com.seniorlibs.algorithm.linkedlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.seniorlibs.algorithm.R
import com.seniorlibs.algorithm.map.MapActivity
import com.seniorlibs.baselib.utils.LogUtils

/**
 * Week3
 */
class Week3 {

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        // 构造哈希映射，帮助我们快速定位根节点
        val map: MutableMap<Int, Int> = mutableMapOf()
        for (i in inorder.indices) {
            map[inorder[i]] = i
        }

        return buildTreeHelper(preorder, 0, preorder.size, inorder, 0, inorder.size, map)
    }

    private fun buildTreeHelper(preorder: IntArray, pStart: Int, pEnd: Int, inorder: IntArray, iStart: Int, iEnd: Int, map: MutableMap<Int, Int>): TreeNode? {
        // preorder为空，直接返回null
        if (pStart == pEnd) return null

        val rootVal = preorder[pStart]
        val root = TreeNode(rootVal)
        // 在中序遍历中找到根节点的位置
        val iRootIndex = map[rootVal]!!

        val leftNum = iRootIndex - iStart

        // 递归的构造左子树
        root.left = buildTreeHelper(preorder, pStart + 1, pStart + leftNum + 1, inorder, iStart, iRootIndex, map)
        // 递归的构造右子树
        root.right = buildTreeHelper(preorder, pStart + leftNum + 1, pEnd, inorder, iRootIndex + 1, iEnd, map)
        return root
    }

    /**
     * 98. 验证二叉搜索树
     *
     * 方法二：中序遍历
     * 思路：二叉搜索树「中序遍历」得到的值构成的序列一定是升序的，如果当前节点小于等于中序遍历的前一个节点，说明不满足BST，返回false；否则满足
     *
     * 时间复杂度 : O(n)，二叉树的每个节点最多被访问一次，因此时间复杂度为O(n)。
     * 空间复杂度 : O(n)，由于使用递归，将会使用隐式栈空间，递归深度会达到 n 层。
     *
     * 题解：
     * @param root
     * @return
     */
    var pre = Long.MIN_VALUE
    var flag: Boolean = true  // 当前节点小于是否大于前一个节点标记

    fun isValidBST(root: TreeNode?): Boolean {
        if (root == null) return true

        if (flag) isValidBST(root.left)       // 遍历左子树

        if (root.`val` <= pre) flag = false   // 如果当前节点小于等于中序遍历的前一个节点，说明不是BST，flag = false

        pre = root.`val`.toLong()             // 记录前一个节点

        if (flag) isValidBST(root.right)      // 遍历右子树

        return flag
    }

    /**
     * 98. 验证二叉搜索树
     *
     * 方法一：递归
     * 如果该二叉树的左子树不为空，则左子树上所有节点均小于它的根节点；若它的右子树不空，则右子树上所有节点均大于它的根节点；则满足BST
     *
     * 题解：
     * @param root
     * @return
     */
    fun isValidBST1(root: TreeNode?): Boolean {
        return solution(root, Long.MIN_VALUE, Long.MAX_VALUE)
    }

    private fun solution(root: TreeNode?, minValue: Long, maxValue: Long): Boolean {
        if (root == null) return true

        if (root.`val` <= minValue || root.`val` >= maxValue) return false  // 左子树节点小于它的根节点

        return solution(root.left, minValue, root.`val`.toLong())
                && solution(root.right, root.`val`.toLong(), maxValue)
    }

    /**
     * 144. 二叉树的前序遍历：迭代
     *
     * 时间复杂度：O(n)，二叉树的每个节点最多被访问一次，因此时间复杂度为O(n)。
     * 空间复杂度：O(n)，空间复杂度与系统堆栈有关，系统栈需要记住每个节点的值，所以空间复杂度为O(n)。
     *
     * 题解：https://leetcode-cn.com/problems/binary-tree-preorder-traversal/solution/144-er-cha-shu-de-qian-xu-bian-li-by-chen-li-guan/
     * @param root
     * @return
     */
    fun preorderTraversal(root: TreeNode?): List<Int> {
        val res: MutableList<Int> = mutableListOf()
        val stack: Deque<TreeNode?> = LinkedList()

        if (root == null) return res else stack.push(root)

        while (!stack.isEmpty()) {
            val p = stack.pop()    // 将根节点弹出，如果为标记null，则是将空节点弹出即可；如果不为null，下面再将根节点添加到栈中
            if (p != null) {
                if (p.right != null) stack.push(p.right)   // 添加右节点
                if (p.left != null) stack.push(p.left)     // 添加左节点
                stack.push(p)                  // 添加根节点
                stack.push(null)            // 根节点访问过，但还没有处理，需要做一下标记null
            } else {                           // 遇到标记，弹出栈顶元素，加入到集合中
                res.add(stack.pop()!!.`val`)
            }
        }
        return res
    }

    /**
     * 94. 二叉树的中序遍历：迭代
     *
     * 时间复杂度：O(n)，二叉树的每个节点最多被访问一次，因此时间复杂度为O(n)。
     * 空间复杂度：O(n)，空间复杂度与系统堆栈有关，系统栈需要记住每个节点的值，所以空间复杂度为O(n)。
     *
     * 题解：https://leetcode-cn.com/problems/binary-tree-inorder-traversal/solution/94-er-cha-shu-de-zhong-xu-bian-li-by-chen-li-guan/
     * @param root
     * @return
     */
    fun inorderTraversal(root: TreeNode?): List<Int> {
        val res: MutableList<Int> = mutableListOf()
        val stack: Deque<TreeNode?> = LinkedList()

        if (root == null) return res else stack.push(root)

        while (!stack.isEmpty()) {
            val p = stack.pop()     // 将根节点弹出，如果为标记null，则是将空节点弹出即可；如果不为null，下面再将根节点添加到栈中
            if (p != null) {
                if (p.right != null) stack.push(p.right)   // 添加右节点
                stack.push(p)                  // 添加根节点
                stack.push(null)            // 根节点访问过，但还没有处理，需要做一下标记null
                if (p.left != null) stack.push(p.left)     // 添加左节点
            } else {
                res.add(stack.pop()!!.`val`)   // 遇到标记，弹出栈顶元素，加入到集合中
            }
        }
        return res
    }

    /**
     * 145. 二叉树的后序遍历：迭代
     *
     * 时间复杂度：O(n)，二叉树的每个节点最多被访问一次，因此时间复杂度为O(n)。
     * 空间复杂度：O(n)，空间复杂度与系统堆栈有关，系统栈需要记住每个节点的值，所以空间复杂度为O(n)。
     *
     * 题解：https://leetcode-cn.com/problems/binary-tree-postorder-traversal/solution/145-er-cha-shu-de-hou-xu-bian-li-by-chen-li-guan/
     * @param root
     * @return
     */
    fun postorderTraversal(root: TreeNode?): List<Int> {
        val res: MutableList<Int> = mutableListOf()
        val stack: Deque<TreeNode?> = LinkedList()

        if (root == null) return res else stack.add(root)

        while (!stack.isEmpty()) {
            val p = stack.pop()    // 将根节点弹出，如果为标记null，则是将空节点弹出即可；如果不为null，下面再将根节点添加到栈中
            if (p != null) {
                stack.push(p)                  // 添加根节点
                stack.push(null)            // 根节点访问过，但还没有处理，需要做一下标记null
                if (p.right != null) stack.push(p.right)    // 添加右节点
                if (p.left != null) stack.push(p.left)      // 添加左节点
            } else {                           // 遇到标记，模拟执行方法内部操作
                res.add(stack.pop()!!.`val`)
            }
        }
        return res
    }

    /**
     * 111.二叉树的最小深度
     *
     * 时间复杂度：O(n)，二叉树的每个节点最多被访问一次，因此时间复杂度为O(n)。
     * 空间复杂度：O(n)，由于使用递归，将会使用隐式栈空间，递归深度会达到 n 层。
     *
     * @param root
     * @return
     */
    fun minDepth(root: TreeNode?): Int {
        if (root == null) return 0

        // 左子树的最小深度
        val left = minDepth(root.left)
        // 右子树的最小深度
        val right = minDepth(root.right)

        // 如果left和right都为0，返回1即可;
        // 如果left和right只有一个为0，说明他只有一个子结点，只需要返回它另一个子节点的最小深度+1即可;
        // 如果left和right都不为0，说明他有两个子节点，只需要返回最小深度的+1即可。
        return if ((left == 0 || right == 0)) left + right + 1 else Math.min(left, right) + 1
    }

    /**
     * 590. N叉树的后序遍历
     *
     * @param root
     * @return
     */
    private fun postorder(root: Node?): List<Int> {
        if (root == null) {
            return list
        }

        root.children.forEach {
            postorder(it)
        }

        list.add(root.`val`)

        return list
    }

    /**
     * 70. 爬楼梯
     * 方法一：动态规划
     *
     * 时间复杂度：循环执行n次，每次花费常数的时间代价，时间复杂度为 O(n)；
     * 空间复杂度：用了n空间的数组辅助，空间复杂度为 O(n)。
     *
     * @param n
     * @return
     */
    fun climbStairs(n: Int): Int {
        if (n <= 1) return 1

        val array = IntArray(n + 1)

        array[1] = 1
        array[2] = 2

        for (i in 3 until n + 1) {
            array[i] = array[i - 1] + array[i - 2]
        }

        return array[n]
    }

    /**
     * 70. 爬楼梯
     * 方法二：动态规划优化，斐波那契数。数组当前值是依赖他前面两个值的（前两个除外），我们只需要用两个临时变量即可，不需要申请一个数组
     *
     * 时间复杂度：循环执行n次，每次花费常数的时间代价，时间复杂度为 O(n)；
     * 空间复杂度：只用了常数个变量作为辅助空间，空间复杂度为 O(1)。
     *
     * @param n
     * @return
     */
    fun climbStairs1(n: Int): Int {
        if (n <= 2) return n

        var first = 1
        var second = 2
        var sum = 0

        for (i in 3 until n + 1) {
            sum = first + second
            first = second
            second = sum
        }

        return sum
    }

    /**
     * 70. 爬楼梯
     * 方法三：暴力递归
     *
     * 时间复杂度：O(2^n)。树形递归的大小为2^n；
     * 空间复杂度：O(n)。递归树的深度可以达到n
     *
     * @param n
     * @return
     */
    fun climbStairs3(n : Int) : Int {
        if (n <= 2) return n

        return climbStairs3(n - 1) + climbStairs3(n - 2)
    }

    /**
     * 70. 爬楼梯
     * 方法三：记忆化递归
     *
     * 时间复杂度：O(n)。树形递归的大小可以达到 n；
     * 空间复杂度：O(n)。递归树的深度可以达到 n
     *
     * @param n
     * @return
     */
    fun climbStairs4(n : Int, first : Int, second : Int) : Int {
        if (n <= 1) return second

        return climbStairs4(n - 1, second, first + second)
    }


    /**
     * 回溯法 的中心思想：像这种强烈暗示要暴力遍历所有组合（遍历决策树）的题，明显应该采用回溯法
     */

    /**
     * 22. 括号生成
     * 思路：left随时可以加，只要别用完(n) ; right必须之前有左括号，左个数>右个数
     *
     * @param n
     * @return
     */
    fun generateParenthesis(n: Int): List<String> {
        generate(0 ,0,  n, "")
        return list
    }

    val list : MutableList<String> = mutableListOf()

    private fun generate(left : Int, right : Int, n : Int, s : String) {
        // 1.递归终结条件（最先写）
        if (left > n || left < right) { // 肯定不合法，提前结束，即“剪枝”
            return
        }
        if (left == n && right == n) {
            list.add(s)
            return
        }

        // 2.处理当前层逻辑

        // 3.下探到下一层
        if (left < n) {  // left随时可以加，只要别用完(n)
            generate(left + 1, right, n, s + "(")
        }

        if (left > right) { // right必须之前有左括号，左个数>右个数
            generate(left, right + 1, n, s + ")")
        }

        // 4.清理恢复当前层
    }
}