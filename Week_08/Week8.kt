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
 * Week8
 */
class Week8 {

    /**
     * 191. 位1的个  方法1：循环和位移动
     *
     * 思路：遍历数字的 32 位。如果某一位是 1 ，将计数器加一
     *
     * 时间复杂度：O(1) 。运行时间与n中位为1的有关。在最坏情况下， n中所有位都是1 。对于32位整数，运行时间是O(1)的。
     * 空间复杂度：O(1) 。没有使用额外空间。
     *
     * @param n
     * @return
     */
    fun hammingWeight(n: Int): Int {
        var bits = 0
        var mask = 1
        for (i in 0..31) {
            // 00000000000000000000001000001001 & 1 / 1000 / 100000000 != 0
            if (n and mask != 0) {
                bits++
            }
            mask = mask shl 1  // 左移<<=， 1 / 10 / 100 / 1000 / ... / 1000000000000000000000000000000
        }
        return bits
    }

    /**
     * 191. 位1的个  方法2：位操作的小技巧（方法1的优化）
     *
     * 思路：对于任意数字 n ，将 n 和 n−1 做与运算，会把最后一个 1 的位变成 0
     *
     * 时间复杂度：O(1) 。运行时间与n中位为1的有关。在最坏情况下， n中所有位都是1 。对于32位整数，运行时间是O(1)的。
     * 空间复杂度：O(1) 。没有使用额外空间。
     *
     * @param n
     * @return
     */
    fun hammingWeight1(n: Int): Int {
        var n = n
        var sum = 0
        while (n != 0) {
            sum++
            // 00000000000000000000001000001001 & 00000000000000000000001000001000 = 00000000000000000000001000001000
            // 00000000000000000000001000001000 & 00000000000000000000001000000111 = 00000000000000000000001000000000
            // 00000000000000000000001000000000 & 00000000000000000000000111111111 = 00000000000000000000000000000000
            n = n and n - 1
        }
        return sum
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
     * 231. 2的幂
     *
     * 若 n = 2^x ，且 x 为自然数（即 n 为 2 的幂），则一定满足以下条件：
     * 1.恒有 n & (n - 1) == 0，这是因为：
     *  （1）n 二进制最高位为 1，其余所有位为 0；
     *  （2）n−1 二进制最高位为 00，其余所有位为 11；
     * 2.一定满足 n > 0。
     *
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     *
     * @param n
     * @return
     */
    fun isPowerOfTwo(n: Int): Boolean {
        return n > 0 && n and n - 1 == 0
    }

    class LRUCache {
        internal class DLinkedNode {
            var key = 0
            var value = 0
            var prev: DLinkedNode? = null
            var next: DLinkedNode? = null

            constructor() {}
            constructor(_key: Int, _value: Int) {
                key = _key
                value = _value
            }
        }

        private val cache: MutableMap<Int, DLinkedNode> = HashMap()
        private var size = 0
        private var capacity = 0
        private var head: DLinkedNode? = null
        private var tail:DLinkedNode? = null

        fun LRUCache(capacity: Int) {
            size = 0
            this.capacity = capacity
            // 使用伪头部和伪尾部节点
            head = DLinkedNode()
            tail = DLinkedNode()
            head!!.next = tail
            tail!!.prev = head
        }

        operator fun get(key: Int): Int {
            val node = cache[key] ?: return -1
            // 如果 key 存在，先通过哈希表定位，再移到头部
            moveToHead(node)
            return node.value
        }

        fun put(key: Int, value: Int) {
            val node = cache[key]
            if (node == null) {
                // 如果 key 不存在，创建一个新的节点
                val newNode = DLinkedNode(key, value)
                // 添加进哈希表
                cache[key] = newNode
                // 添加至双向链表的头部
                addToHead(newNode)
                ++size
                if (size > capacity) {
                    // 如果超出容量，删除双向链表的尾部节点
                    val tail = removeTail()
                    // 删除哈希表中对应的项
                    cache.remove(tail.key)
                    --size
                }
            } else {
                // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
                node.value = value
                moveToHead(node)
            }
        }

        private fun addToHead(node: DLinkedNode) {
            node.prev = head
            node.next = head!!.next
            head!!.next!!.prev = node
            head!!.next = node
        }

        private fun removeNode(node: DLinkedNode) {
            node.prev!!.next = node.next
            node.next!!.prev = node.prev
        }

        private fun moveToHead(node: DLinkedNode) {
            removeNode(node)
            addToHead(node)
        }

        private fun removeTail(): DLinkedNode {
            val res: DLinkedNode = tail?.prev!!
            removeNode(res)
            return res
        }
    }
}