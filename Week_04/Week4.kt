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
 * Week4
 */
class Week4 {

    /**
     * 69. x 的平方根。方法一：二分查找
     *
     * 思想：一个数的平方根肯定不会超过它自己，最小是0，范围最大是自己。例如 ：8的平方根，8的一半是4，4^2=16>8，
     *      意即：如果一个数的一半的平方大于它自己，那么这个数的取值范围在小于4的一半：0-3
     *
     * 时间复杂度：O(logX)，二分法的时间复杂度是对数级别的。
     * 空间复杂度：O(1)，使用了常数个数的辅助空间用于存储和比较。
     *
     * https://leetcode-cn.com/problems/sqrtx/solution/69-x-de-ping-fang-gen-by-chen-li-guan-2/
     */
    fun mySqrt(x: Int): Int {
        var left: Long = 0
        var right: Long = x.toLong()
        var mid: Long = 0
        var result: Long = -1

        while (left <= right) {
            mid = left + (right - left) / 2

            if (mid * mid <= x) { // 2.82842..., 由于返回类型是整数，小数部分将被舍去，结果是2。所以结果在mid*mid<x这一边
                result = mid
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return result.toInt()
    }

    /**
     * 69. x 的平方根。方法二：牛顿迭代，
     *
     * 思想：v = (v + a/v)/2
     *
     * 时间复杂度：O(logX)，此方法是二次收敛的，相较于二分查找更快。。
     * 空间复杂度：O(1)，使用了常数个数的辅助空间用于存储和比较。
     *
     * https://leetcode-cn.com/problems/sqrtx/solution/69-x-de-ping-fang-gen-by-chen-li-guan-2/
     */
    fun mySqrt1(x: Int): Int {
        var v = x.toLong()
        while (x < v * v) {
            v = (v + x / v) / 2
        }
        return v.toInt()
    }


    /**
     * 33. 搜索旋转排序数组  5,6,7,0,1,2,3,4  6
     *
     * 思想：循环判断，直到排除到只剩最后一个元素时，退出循环，如果该元素和 target 相同，直接返回下标，否则返回 -1。
     * 步骤：1.当[0,mid]升序时: nums[0] <= nums[mid]，当target > nums[mid] || target < nums[0]，target不在[0,mid]中，则向后规约条件
     *      2.当[0,mid]发生旋转时: nums[0] > nums[mid]，当target > nums[mid] && target < nums[0]，target不在[0,mid]中，向后规约条件
     *      3.其他其他情况就是向前规约了
     *
     * 时间复杂度：O(logn)，其中n为nums数组的大小。整个算法时间复杂度即为二分搜索的时间复杂度O(logn)。
     * 空间复杂度：O(1) 。只需要常数级别的空间存放变量。
     * @param nums
     * @param target
     * @return
     */
    fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1
        var mid = 0
        while (left < right) {
            mid = (right - left) / 2 + left

            if (nums[mid] == target) {
                return mid
            }
            // 当[0,mid]升序时: nums[0] <= nums[mid]，当target > nums[mid] || target < nums[0]，target不在[0,mid]中，则向后规约条件
            if (nums[0] <= nums[mid] && (target < nums[0] || target > nums[mid])) {
                left = mid + 1
                // 当[0,mid]发生旋转时: nums[0] > nums[mid]，当target > nums[mid] && target < nums[0]，target不在[0,mid]中，向后规约条件
            } else if (target < nums[0] && target > nums[mid]) {
                left = mid + 1
                // 其他其他情况就是向前规约了
            } else {
                right = mid - 1
            }
        }

        return -1
    }

    /**
     * 122. 买卖股票的最佳时机 II（允许多次买卖，必须在再次购买前卖掉之前的股票）
     *
     * 思想：1.连续上涨交易日：每天都买卖，则第一天买最后一天卖收益最大；
     *      2.连续下降交易日：则不买卖收益最大，即不会亏钱；
     *      3.只要当前价格大于前一天价格，就把利润锁定。
     *
     * 时间复杂度：O(n)，遍历一次；
     * 空间复杂度：O(1)，需要常量的空间。
     */
    fun maxProfit(prices: IntArray): Int {
        var profit = 0
        for (i in 1 until prices.size) {
            val tmp = prices[i] - prices[i - 1]  // 设 tmp 为第 i-1 日买入与第 i 日卖出赚取的利润
            if (tmp > 0) profit += tmp
        }
        return profit
    }

    /**
     * 200. 岛屿数量——方法一：深度优先遍历DFS
     *
     * 目标：是找到矩阵中 “岛屿的数量” ，上下左右相连的 1 都被认为是连续岛屿。
     * 思想：遍历整个矩阵，当遇到 grid[i][j] == '1' 时，从此点开始做深度优先搜索 dfs，岛屿数 count + 1 且在深度优先搜索中删除此岛屿。
     *
     * 步骤：1.从岛屿中的某一点 (i, j)向此点的上下左右 (i+1,j),(i-1,j),(i,j+1),(i,j-1) 做深度搜索；
     *      2.终止条件：(i, j) 越过矩阵边界; grid[i][j] == 0，代表此分支已越过岛屿边界；
     *      3.搜索岛屿的同时，执行grid[i][j] = '0'，即将岛屿所有节点删除，以免之后重复搜索相同岛屿。
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(mn)，在最坏情况下，整个网格均为陆地，深度优先搜索的深度达到mn
     *
     * https://leetcode-cn.com/problems/number-of-islands/solution/200-dao-yu-shu-liang-dfsbfs-by-chen-li-guan/
     * @param grid
     * @return
     */
    fun numIslands(grid: Array<CharArray>): Int {
        var count = 0
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j)
                    count++
                }
            }
        }
        return count
    }

    private fun dfs(grid: Array<CharArray>, i: Int, j: Int) {
        if (i >= 0 && i < grid.size && j >= 0 && j < grid[0].size && grid[i][j] == '1') {
            grid[i][j] = '0'
            dfs(grid, i + 1, j)
            dfs(grid, i, j + 1)
            dfs(grid, i - 1, j)
            dfs(grid, i, j - 1)
        }
    }

    /**
     * 200. 岛屿数量——方法二：广度优先遍历BFS
     *
     * 步骤：1.借用一个队列 queue，判断队列首部节点 (i, j) 是否未越界且为1：
     *       （1）若是则置零（删除岛屿节点），并将此节点上下左右节点 (i+1,j),(i-1,j),(i,j+1),(i,j-1)加入队列；
     *       （2）若不是则跳过此节点；
     *      2.循环 poll 队列首节点，直到整个队列为空，此时已经遍历完此岛屿。
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数。
     * 空间复杂度：O(mn)，在最坏情况下，整个网格均为陆地，深度优先搜索的深度达到mn
     *
     * https://leetcode-cn.com/problems/number-of-islands/solution/200-dao-yu-shu-liang-dfsbfs-by-chen-li-guan/
     * @param grid
     * @return
     */
    fun numIslands1(grid: Array<CharArray>): Int {
        var count = 0
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '1') {
                    bfs(grid, i, j)
                    count++
                }
            }
        }
        return count
    }

    private fun bfs(grid: Array<CharArray>, i: Int, j: Int) {
        var i = i
        var j = j

        val queue: Queue<IntArray> = LinkedList()
        queue.offer(intArrayOf(i, j))
        while (!queue.isEmpty()) {
            val cur = queue.poll()
            i = cur[0]
            j = cur[1]

            if (i >= 0 && i < grid.size && j >= 0 && j < grid[0].size && grid[i][j] == '1') {
                grid[i][j] = '0'
                queue.offer(intArrayOf(i + 1, j))
                queue.offer(intArrayOf(i - 1, j))
                queue.offer(intArrayOf(i, j + 1))
                queue.offer(intArrayOf(i, j - 1))
            }
        }
    }

    /**
     * 515. 在每个树行中找最大值 -- 方法一：BFS广度遍历-迭代：
     *
     * 思想：每次递归的时候都需要带一个 index(表示当前的层数)，也就对应那个田字格子中的第几行，如果 当前值 比 此位置的值 大，就用 当前值 覆盖。
     *
     * 时间复杂度：O(n)，每个点进队出队各一次，故渐进时间复杂度为 O(n)；
     * 空间复杂度：O(n)，队列中元素的个数不超过 nn 个，故渐进空间复杂度为 O(n)。
     *
     * https://leetcode-cn.com/problems/find-largest-value-in-each-tree-row/solution/515-zai-mei-ge-shu-xing-zhong-zhao-zui-da-zhi-by-c/
     *
     * @param root
     * @return
     */
    fun largestValues(root: TreeNode?): List<Int> {
        val res: MutableList<Int> = mutableListOf()   // 存放最终结果的集合
        if (root == null) return res

        val deque: Deque<TreeNode> = LinkedList()     // 创建一个队列，将根节点放入其中
        deque.offer(root)

        while (!deque.isEmpty()) {
            var max: Int = Int.MIN_VALUE
            val size = deque.size         // 每次遍历的数量为队列的长度

            for (i in 0 until size) {     // 将这一层的元素全部取出
                val node = deque.poll()
                if (node?.`val`!! > max) {    // 记录每层的最大值
                    max = node.`val`
                }

                val left = node.left
                val right = node.right
                if (left != null) deque.offer(left)   // 如果节点的左右孩子不为空，放入队列
                if (right != null) deque.offer(right)
            }

            res.add(max)
        }

        return res
    }

    /**
     * 515. 在每个树行中找最大值 -- 方法二：DFS深度遍历-递归：
     *
     * 时间复杂度：O(n)，每个点进队出队各一次，故渐进时间复杂度为 O(n)；
     * 空间复杂度：O(h)，h 是树的高度。
     *
     * https://leetcode-cn.com/problems/find-largest-value-in-each-tree-row/solution/515-zai-mei-ge-shu-xing-zhong-zhao-zui-da-zhi-by-c/
     * @param root
     * @return
     */
    fun largestValues1(root: TreeNode?): List<Int> {
        val res: MutableList<Int> = mutableListOf()
        if (root == null) return res

        dfsLargest(1, root, res)
        return res
    }

    fun dfsLargest(index: Int, root: TreeNode?, res: MutableList<Int>) {
        if (root == null) return

        if (index > res.size) {
            res.add(index - 1, Int.MIN_VALUE)
        }
        if (root.`val` > res[index - 1]) {  // 如果 当前值 比 此位置的值 大，就用 当前值 覆盖。
            res[index - 1] = root.`val`
        }

        if (root.left != null) {   // 递归的处理左子树，右子树，同时将层数index+1
            dfsLargest(index + 1, root.left, res)
        }
        if (root.right != null) {
            dfsLargest(index + 1, root.right, res)
        }
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     *
     * 核心思想：前序遍历的根节点始终出现在数组的第一位，而中序遍历中根节点出现在数组的中间位置
     *
     * 时间复杂度：O(n)，其中n是树中的节点个数；
     * 空间复杂度：O(n)，除去返回的答案需要的O(n)空间之外，还需要使用O(n)的空间存储哈希映射，
     *       以及O(h)（其中h是树的高度）的空间表示递归时栈空间。这里h<n，所以总空间复杂度为O(n)。
     * @param preorder
     * @param inorder
     * @return
     */
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        // 构造哈希映射，帮助我们快速定位中序数组根节点
        val map: MutableMap<Int, Int> = mutableMapOf()
        for (i in inorder.indices) {
            map[inorder[i]] = i
        }

        return buildTreeHelper(preorder, 0, preorder.size, inorder, 0, inorder.size, map)
    }

    private fun buildTreeHelper(preorder: IntArray, pStart: Int, pEnd: Int,
                                inorder: IntArray, iStart: Int, iEnd: Int,
                                map: MutableMap<Int, Int>): TreeNode? {

        if (pStart == pEnd) return null   // 前序数组为空，直接返回null

        val rootValue = preorder[pStart]     // 在前序数组中找到根节点值
        val rootNode = TreeNode(rootValue)        // 构造根节点
        val iRootIndex = map[rootValue]!!    // 在中序数组中找到根节点下标

        val leftNum = iRootIndex - iStart    // 中序数组的根节点下标 与 中序起点下标 差距

        rootNode.left = buildTreeHelper(preorder, pStart + 1, pStart + 1 + leftNum ,
                inorder, iStart, iRootIndex, map)    // 递归的构造左子树

        rootNode.right = buildTreeHelper(preorder, pStart + 1 + leftNum, pEnd,
                inorder, iRootIndex + 1, iEnd, map)  // 递归的构造右子树
        return rootNode
    }

    /**
     * 107. 二叉树的层次遍历 -- 方法一：BFS广度遍历-迭代
     *
     * 时间复杂度：O(n)，每个点进队出队各一次，故渐进时间复杂度为 O(n)；
     * 空间复杂度：O(n)，队列中元素的个数不超过 nn 个，故渐进空间复杂度为 O(n)。
     *
     * @param root
     * @return
     */
    fun levelOrder(root: TreeNode?): List<List<Int>>? {
        val res: MutableList<List<Int>> = mutableListOf()  // 存放最终结果的集合
        if (root == null) {
            return res
        }

        val queue: Queue<TreeNode> = LinkedList()
        queue.offer(root)     // 创建一个队列，将根节点放入其中
        while (!queue.isEmpty()) {
            val level: MutableList<Int> = mutableListOf()
            val size: Int = queue.size   // 每次遍历的数量为队列的长度

            for (i in 0 until size) {   // 将这一层的元素全部取出，放入到结果集合
                val node = queue.poll()
                level.add(node.`val`)

                val left = node.left
                val right = node.right
                if (left != null) queue.offer(left)  // 如果节点的左右孩子不为空，放入队列
                if (right != null) queue.offer(right)
            }

            res.add(level)
        }

        return res
    }


    /**
     * 107. 二叉树的层次遍历 -- DFS深度遍历-递归
     *
     * 思想：每次递归的时候都需要带一个 index(表示当前的层数)，也就对应那个田字格子中的第几行，如果当前行对应的 list 不存在，就加入一个空 list 进去。
     *
     * 时间复杂度：O(n)，每个点进队出队各一次，故渐进时间复杂度为 O(n)；
     * 空间复杂度：O(h)，h 是树的高度。
     *
     * @param root
     * @return
     */
    fun levelOrder1(root: TreeNode?): List<List<Int>>? {
        val res: MutableList<MutableList<Int>> = mutableListOf()  // 存放最终结果的集合
        if (root == null) {
            return res
        }

        dfs(1, root, res)
        return res
    }

    fun dfs(index: Int, root: TreeNode?, res: MutableList<MutableList<Int>>) {
        if (root == null) {
            return
        }

        if (res.size < index) {      // 假设res是[ [1],[2,3] ]， index是3，就再插入一个空list放到res中
            res.add(mutableListOf())
        }

        res[index - 1].add(root.`val`)   // 将当前节点的值加入到res中，index代表当前层，假设index是3，节点值是6。res是[ [1],[2,5],[3,4] ]，加入后res就变为 [ [1],[2,5],[3,4,6] ]

        if (root.left != null) {   // 递归的处理左子树，右子树，同时将层数index+1
            dfs(index + 1, root.left, res)
        }
        if (root.right != null) {
            dfs(index + 1, root.right, res)
        }
    }
}