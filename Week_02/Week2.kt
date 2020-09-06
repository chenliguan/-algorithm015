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
 * Week2
 */
class Week2  {

    /**
     * 206. 反转链表
     *
     * @param head
     * @return
     */
    private fun reverseList(head: ListNode?): ListNode? {
        var cur = head
        var pre : ListNode? = null

        while (cur != null) {
            val temp = cur.next
            cur.next = pre
            pre = cur
            cur = temp
        }

        return pre
    }


    /**
     * N叉数的遍历
     */
    class Node(var `val`: Int) {
        var children = ArrayList<Node>()
    }

    private fun preorder(root: Node?): List<Int> {
        if (root == null) {
            return list
        }

        list.add(root.`val`)
        root.children.forEach {
            preorder(it)
        }

        return list
    }


    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    /**
     *  二叉树的最大深度
     */
    private fun maxDepth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }

        val left = maxDepth(root.left) + 1
        val right = maxDepth(root.right) + 1
        return Math.max(left, right)
    }


    /**
     * 94. 二叉树的中序遍历
     *
     * @param root
     * @return
     */
    private fun inorderTraversal(root: TreeNode?): List<Int> {
        if (root == null) {
            return list
        }

        inorderTraversal(root.left)

        list.add(root.`val`)

        inorderTraversal(root.right)

        return list
    }

    /**
     * 144. 二叉树的前序遍历
     *
     * @param root
     * @return
     */
    private fun preorderTraversal(root: TreeNode?): List<Int?>? {
        if (root == null) {
            return list
        }

        list.add(root.`val`)

        preorderTraversal(root.left)

        preorderTraversal(root.right)

        return list
    }

    /**
     * 145. 二叉树的后序遍历
     *
     * @param root
     * @return
     */
    fun postorderTraversal(root: TreeNode?): List<Int> {
        if (root == null) {
            return list
        }

        postorderTraversal(root.left)

        postorderTraversal(root.right)

        list.add(root.`val`)

        return list
    }


    /**
     * 242. 有效的字母异位词：哈希表
     *
     * @param s
     * @param t
     * @return
     */
    private fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) {
            return false
        }

        val sArray = s.toCharArray()
        val tArray = t.toCharArray()

        val count = IntArray(26)
        for (i in sArray.indices) {
            count[sArray[i] - 'a']++
            count[tArray[i] - 'a']--
        }

        for (c in count) if (c != 0) return false

        return true
    }

    /**
     * 242. 有效的字母异位词：排序
     *
     * @param s
     * @param t
     * @return
     */
    private fun isAnagram1(s: String, t: String): Boolean {
        if (s.length != t.length) {
            return false
        }

        val sChar = s.toCharArray()
        val tChar = t.toCharArray()
        Arrays.sort(sChar)
        Arrays.sort(tChar)

        return sChar.contentEquals(tChar)
    }

    /**
     * 49. 字母异位词分组
     *
     * @param strs
     * @return
     */
    private fun groupAnagrams(strs: Array<String>): List<List<String>> {
        if (strs.isEmpty()) return mutableListOf()

        val map = hashMapOf<String, MutableList<String>>()
        val count = IntArray(26)

        for (str in strs) {
            Arrays.fill(count, 0) // 将count数组清零

            for (c in str.toCharArray()) {
                count[c - 'a']++
            }

            val sb = StringBuilder()
            for (i in count) { // 将每个字符串s转换为字符数组count-->key，由26个非负整数组成，表示a，b，c的数量。"eat", "tea"的key是相等的
                sb.append("#${i}")
            }

            val key = sb.toString()
            if (!map.containsKey(key)) {
                map[key] = mutableListOf(str)
            } else{
                map[key]?.add(str)
            }
        }

        return ArrayList(map.values)
    }

    /**
     * 412. Fizz buzz
     *
     * @param n
     * @return
     */
    private fun fizzBuzz(n: Int): List<String> {
        val map = mapOf(3 to "Fizz", 5 to "Buzz")
        val list = mutableListOf<String>()

        for (i in 1 until n + 1) {  // 排除0 % 3 == 0
            var value = ""
            for (key in map.keys) {
                if (i % key == 0) {
                    value += map[key]
                }
            }

            if (value.isEmpty()) {
                value += i
            }

            list.add(value)
        }

        return list
    }

    /**
     * 347. 前 K 个高频元素
     *
     * @param nums
     * @param k
     * @return
     */
    @RequiresApi(Build.VERSION_CODES.N)
    open fun topKFrequent(nums: IntArray, k: Int): MutableList<Int> {
        // 使用字典，统计每个元素出现的次数，元素为键，元素出现的次数为值
        val map: HashMap<Int, Int> = HashMap()
        for (num in nums) {
            if (map.containsKey(num)) {
                map[num] = map[num]!! + 1
            } else {
                map[num] = 1
            }
        }
        // 遍历map，用最小堆保存频率最大的k个元素
        val pq: PriorityQueue<Int> = PriorityQueue(object : Comparator<Int?> {
            override fun compare(o1: Int?, o2: Int?): Int {
                return map[o1]!! - map[o2]!!
            }
        })
        for (key in map.keys) {
            if (pq.size < k) {
                pq.add(key)
            } else if (map[key]!! > map[pq.peek()]!!) {
                pq.remove()
                pq.add(key)
            }
        }
        // 取出最小堆中的元素
        val res: MutableList<Int> = ArrayList()
        while (!pq.isEmpty()) {
            res.add(pq.remove())
        }
        return res
    }
}