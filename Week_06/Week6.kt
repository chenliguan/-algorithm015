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
 * Week6
 */
class Week6 {

    /**
     * 动态规划
     */

    /**
     * 70. 爬楼梯  方法一：动态规划
     *
     * 时间复杂度：循环执行n次，每次花费常数的时间代价，时间复杂度为 O(n)；
     * 空间复杂度：用了n空间的数组辅助，空间复杂度为 O(n)。
     *
     * @param n
     * @return
     */
    fun climbStairs(n: Int): Int {
        if (n <= 2) return 2

        val res = IntArray(n + 1)

        res[1] = 1
        res[2] = 2

        for (i in 3 until n + 1) {
            res[i] = res[i - 1] + res[i - 2]
        }

        return res[n]
    }

    /**
     * 70. 爬楼梯  方法二：动态规划优化，斐波那契数。数组当前值是依赖他前面两个值的（前两个除外），我们只需要用两个临时变量即可，不需要申请一个数组
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
     * 70. 爬楼梯 方法三：暴力递归
     *
     * 时间复杂度：O(2^n)。树形递归的大小为2^n；
     * 空间复杂度：O(n)。递归树的深度可以达到n
     *
     * @param n
     * @return
     */
    fun climbStairs3(n: Int): Int {
        if (n <= 2) return n

        return climbStairs3(n - 1) + climbStairs3(n - 2)
    }

    /**
     * 70. 爬楼梯  方法三：记忆化递归
     *
     * 时间复杂度：O(n)。树形递归的大小可以达到 n；
     * 空间复杂度：O(n)。递归树的深度可以达到 n
     *
     * @param n
     * @return
     */
    fun climbStairs4(n: Int): Int {

        return climbStair(n, 1, 2)
    }

    fun climbStair(n: Int, first: Int, second: Int): Int {
        if (n <= 1) return first
        if (n == 2) return second

        return climbStair(n - 1, second, first + second)  // 5 -> 4 -> 3 (2->1)
    }


    /**
     * 509. 斐波那契数  解法一：暴力递归(自顶向下)
     *
     * 时间复杂度：O(2^N)。这是计算斐波那契数最慢的方法。因为它需要指数的时间。
     * 空间复杂度：O(N)，在堆栈中需要与 N 成正比的空间大小。该堆栈跟踪 fib(N) 的函数调用，随着堆栈的不断增长如果没有足够的内存则会导致 StackOverflowError。
     *
     * @param n
     * @return
     */
    fun fib1(n: Int): Int {
        if (n < 2) return n

        return fib1(n - 1) + fib1(n - 2)
    }

    /**
     * 509. 斐波那契数   解法二：备忘录递归(自顶向下)，解法一递归的升级版
     *
     * 思想：比解法一多了个"备忘录"储存，"剪枝"处理技巧，可以去除重复的调用计算
     *
     * 时间复杂度：O(N)。
     * 空间复杂度：O(N)，内存中使用的堆栈大小
     *
     * @param n
     * @return
     */
    private var cache = arrayOfNulls<Int>(31)

    fun fib2(n: Int): Int {
        if (n < 2) return n

        cache[0] = 0
        cache[1] = 1
        return memoize(n)
    }

    private fun memoize(n: Int): Int {
        if (cache[n] != null) {
            return cache[n]!!   // 如果 N 对应的斐波那契数存在，则返回
        }

        cache[n] = memoize(n - 1) + memoize(n - 2)  // 计算 N 对应的斐波那契数为 memoize(N-1) + memoize(N-2)
        return memoize(n)
    }

    /**
     * 509. 斐波那契数  解法三：动态规划(自底向上)，解法二备忘录存储技巧的升级
     *
     * 思想：自底向上通过迭代计算子问题并存储已计算的值，通过已计算的值进行计算，减少递归带来的重复计算
     *
     * 时间复杂度：O(N)。
     * 空间复杂度：O(N)，使用了空间大小为 N 的数组
     *
     * @param n
     * @return
     */
    fun fib3(n: Int): Int {
        if (n < 2) return n

        val res = IntArray(n + 1)

        res[0] = 0
        res[1] = 1

        for (i in 2 until n + 1) {
            res[i] = res[i - 1] + res[i - 2]
        }

        return res[n]
    }

    /**
     * 509. 斐波那契数   解法四：动态规划(自底向上)
     *
     * 解法三的优化，利用(状态压缩技巧)，其实状态只跟前一个数和当前数有关，不需要像第三种解法那样用一个数组进行存储，只需用两个值存储即可
     *
     * 时间复杂度：O(N)。
     * 空间复杂度：O(1)，仅仅使用了 current，prev1，prev2
     *
     * @param n
     * @return
     */
    fun fib4(n: Int): Int {
        if (n == 0) return 0
        if (n == 1 || n == 2) return 1

        var pre = 1   // fib(1)
        var cur = 1   // fib(2)

        for (i in 3 until n + 1) {
            val sum = pre + cur
            pre = cur
            cur = sum
        }

        return cur
    }


    /**
     * 62. 不同路径  解法一：动态规划(自底向上)
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(mn)，使用了空间大小为mn的数组
     *
     * https://leetcode-cn.com/problems/unique-paths/solution/62-bu-tong-lu-jing-by-chen-li-guan/
     * @param m
     * @param n
     * @return
     */
    fun uniquePaths(m: Int, n: Int): Int {
        if (m == 0 || n == 0) return 0

        // 定义 dp 数组并初始化第 0 行和第 0 列。
        val dp = Array(m) { IntArray(n) }
        var i = 0
        while (i < m) {
            dp[i++][0] = 1
        }
        var j = 0
        while (j < n) {
            dp[0][j++] = 1
        }

        // 根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
        for (i in 1 until m) {
            for (j in 1 until n) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
            }
        }
        return dp[m - 1][n - 1]
    }

    /**
     * 62. 不同路径  解法二：动态规划(自底向上)，空间优化
     *
     * 解法一的优化，利用(状态压缩技巧)，其实状态只跟最近一行有关，不需要像第一种解法那样用两个数组进行存储，只需用一个数组存储即可
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(n)，使用了空间大小为n的数组
     *
     * https://leetcode-cn.com/problems/unique-paths/solution/62-bu-tong-lu-jing-by-chen-li-guan/
     * @param m
     * @param n
     * @return
     */
    fun uniquePaths1(m: Int, n: Int): Int {
        if (m == 0 || n == 0) return 0

        // 定义 dp 数组并初始化第 1 列。
        val dp = IntArray(n)
        var j = 0
        while (j < n) {
            dp[j++] = 1
        }

        for (i in 1 until m) {
            for (j in 1 until n) {
                dp[j] += dp[j - 1]
            }
        }
        return dp[n - 1]
    }


    /**
     * 63. 不同路径 II  解法一：动态规划(自底向上)
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(mn)，使用了空间大小为mn的数组
     *
     * https://leetcode-cn.com/problems/unique-paths-ii/solution/63-bu-tong-lu-jing-ii-by-chen-li-guan-2/
     * @param obstacleGrid
     * @return
     */
    fun uniquePathsWithObstacles(obstacleGrid: Array<IntArray>): Int {
        if (obstacleGrid.isEmpty()) return 0

        val m: Int = obstacleGrid.size
        val n: Int = obstacleGrid[0].size

        // 定义 dp 数组并初始化第 0 行和第 0 列。
        val dp = Array(m) { IntArray(n) }
        var i = 0
        // 一旦遇到值为1的情况，后面的都不会被赋值成1了，都是默认值0
        while (i < m && obstacleGrid[i][0] == 0) {
            dp[i++][0] = 1
        }
        var j = 0
        while (j < n && obstacleGrid[0][j] == 0) {
            dp[0][j++] = 1
        }

        // 根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
        for (i in 1 until m) {
            for (j in 1 until n) {
                // 1是障碍物，0是非障碍物，0才往下迭代
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
                }
            }
        }
        return dp[m - 1][n - 1]
    }

    /**
     * 63. 不同路径 II  解法二：动态规划(自底向上)，空间优化  TODO???
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(n)，使用了空间大小为n的数组
     *
     * https://leetcode-cn.com/problems/unique-paths-ii/solution/63-bu-tong-lu-jing-ii-by-chen-li-guan-2/
     * @param obstacleGrid
     * @return
     */
    fun uniquePathsWithObstacles1(obstacleGrid: Array<IntArray>): Int {
        if (obstacleGrid.isEmpty()) return 0

        val m: Int = obstacleGrid.size
        val n: Int = obstacleGrid[0].size

        // 定义 dp 数组并初始化第 1 列。
        val dp = IntArray(n)
        dp[0] = if (obstacleGrid[0][0] == 0) 1 else 0

        for (i in 0 until m) {
            for (j in 0 until n) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0
                    continue
                }

                if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                    dp[j] += dp[j - 1]
                }
            }
        }
        return dp[n - 1]
    }


    /**
     * 1143. 最长公共子序列  解法一：动态规划（自底向上）
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(mn)，使用了空间大小为mn的数组
     *
     * https://leetcode-cn.com/problems/longest-common-subsequence/solution/1143-zui-chang-gong-gong-zi-xu-lie-by-chen-li-guan/
     * @param text1
     * @param text2
     * @return
     */
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        val m: Int = text1.length
        val n: Int = text2.length
        val c1: CharArray = text1.toCharArray()
        val c2: CharArray = text2.toCharArray()
        val dp = Array(m + 1) { IntArray(n + 1) }

        /* dp默认0行或0列是0 */
        for (i in 1..m) {
            for (j in 1..n) {
                if (c1[i - 1] == c2[j - 1]) {
                    /* 如果末端相同，去找它们各往后退一格的值加1即可 */
                    dp[i][j] = dp[i - 1][j - 1] + 1
                } else {
                    /* 如果末端不同，要么是text1往后退一格，要么是text2往前退一格，两个的最大值 */
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1])
                }
            }
        }
        return dp[m][n]
    }

    /**
     * 120. 三角形最小路径和  解法一：暴力递归(自顶向下)，导致 超时
     *
     * 时间复杂度：O(2^n)，它需要指数的时间。n为三角形的行数。
     * 空间复杂度：O(mn)，在堆栈中需要与 n 成正比的空间大小。n为三角形的行数。
     *
     * @param triangle
     * @return
     */
    fun minimumTotal2(triangle: List<List<Int>>): Int {
        return dfs(triangle, 0, 0)
    }

    private fun dfs(triangle: List<List<Int>>, i: Int, j: Int): Int {
        if (i == triangle.size) {
            return 0
        }
        return Math.min(dfs(triangle, i + 1, j), dfs(triangle, i + 1, j + 1)) + triangle[i][j]
    }

    /**
     * 120. 三角形最小路径和  解法二：备忘录递归(自顶向下)，解法一递归的升级版
     *
     * 思想：比解法一多了个"备忘录"储存，"剪枝"处理技巧，可以去除重复的调用计算
     *
     * 时间复杂度：O(n^2)，n为三角形的行数。
     * 空间复杂度：O(n^2)，n为三角形的行数。
     *
     * @param triangle
     * @return
     */
    private lateinit var res2: Array<Array<Int?>>

    fun minimumTotal3(triangle: List<List<Int>>): Int {
        res2 = Array(triangle.size) { arrayOfNulls<Int>(triangle.size) }

        return dfs1(triangle, 0, 0)
    }

    private fun dfs1(triangle: List<List<Int>>, i: Int, j: Int): Int {
        if (i == triangle.size) {
            return 0
        }

        if (res2[i][j] != null) {
            return res2[i][j]!!
        }

        return Math.min(dfs1(triangle, i + 1, j), dfs1(triangle, i + 1, j + 1)) + triangle[i][j]
    }

    /**
     * 120. 三角形最小路径和  解法一：动态规划（自底向上）
     *
     * 在实际递推中发现，计算dp[i][j] 时，只用到了下一行的dp[i + 1][j]和dp[i + 1][j + 1]。因此dp数组不需要定义n行，只要定义1行就阔以啦.
     *
     * 时间复杂度：O(n^2)，n为三角形的行数。
     * 空间复杂度：O(n^2)，n为三角形的行数。
     *
     * @param triangle
     * @return
     */
    fun minimumTotal(triangle: List<List<Int>>): Int {
        val n = triangle.size
        // dp[i][j] 表示从点(i, j)到底边的最小路径和。
        val dp = Array(n + 1) { IntArray(n + 1) }
        // 从三角形的最后一行开始递推。
        for (i in n - 1 downTo 0) {
            for (j in 0..i) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle[i][j]
            }
        }
        return dp[0][0]
    }

    /**
     * 120. 三角形最小路径和  解法二：动态规划(自底向上)，空间优化
     *
     * 时间复杂度：O(n^2)，n为三角形的行数。
     * 空间复杂度：O(n)，n为三角形的行数。
     *
     * @param triangle
     * @return
     */
    fun minimumTotal1(triangle: List<List<Int>>): Int {
        val n = triangle.size
        val dp = IntArray(n + 1)
        for (i in n - 1 downTo 0) {
            for (j in 0..i) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle[i][j]
            }
        }
        return dp[0]
    }

    /**
     * 53. 最大子序和   解法一：动态规划
     *
     * 思想：数组nums，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * DP方程：sum = max(sum + num[i], num[i])， db = max(db, sum)
     *
     * 时间复杂度：O(n)，其中n为nums数组的长度，只需要遍历一遍数组即可求得答案。
     * 空间复杂度：O(1)，只需要常数空间存放若干变量。
     *
     * @param nums
     * @return
     */
    fun maxSubArray(nums: IntArray): Int {
        var db = nums[0]
        var sum = 0
        for (x in nums) {
//            if (x > 0) {
//                sum += x
//            } else {
//                sum = x
//            }
            sum = Math.max(sum + x, x)
            db = Math.max(db, sum)
        }
        return db
    }

    /**
     * 152. 乘积最大子数组  解法一：动态规划
     *
     * DP方程：imax = max(imax * num[i], num[i])， imin = min(imin * num[i], num[i]), db = max(db, imax)
     *
     * 时间复杂度：程序一次循环遍历了nums，故时间复杂度为O(n)。
     * 空间复杂度：优化后只使用常数个临时变量作为辅助空间，与n无关，故空间复杂度为O(1)。
     *
     * @param nums
     * @return
     */
    fun maxProduct(nums: IntArray): Int {
        var db = Int.MIN_VALUE
        var imax = 1
        var imin = 1
        for (x in nums) {
            if (x < 0) {
                val tmp = imax
                imax = imin
                imin = tmp
            }

            imax = Math.max(imax * x, x)
            imin = Math.min(imin * x, x)
            db = Math.max(db, imax)
        }
        return db
    }

    /**
     * 322. 零钱兑换
     *
     * 时间复杂度：O(Sn)，其中S是金额，n是面额数。一共需要计算O(S)个状态；对于每个状态，每次需要枚举n个面额来转移状态，所以一共需要O(Sn)的时间复杂度；
     * 空间复杂度：O(S)，DP数组需要开长度为总金额S的空间
     *
     * base case：dp[0] = 0
     * DP方程：dp(n) = min{dp(n - coin) + 1}，coin ∈ coins、n > 0
     *
     * https://leetcode-cn.com/problems/coin-change/solution/322-ling-qian-dui-huan-by-chen-li-guan/
     * @param coins
     * @param amount
     * @return
     */
    fun coinChange(coins: IntArray, amount: Int): Int {
        // 为啥dp数组初始化为amount+1呢，因为凑成amount金额的数最多只可能等于amount（全用1元面值的）
        val dp = IntArray(amount + 1)
        Arrays.fill(dp, amount + 1)

        // base case
        dp[0] = 0
        // 外层 for 循环在遍历所有状态的所有取值
        for (n in 1..amount) {
            // 内层 for 循环在遍历硬币的值
            for (coin in coins) {
                // coins[j] > i -> 子问题无解，跳过
                if (coin <= n) {
                    // 求所有选择的最小值
                    dp[n] = Math.min(dp[n], dp[n - coin] + 1)
                }
            }
        }
        return if (dp[amount] > amount) -1 else dp[amount]
    }

    /**
     * 198. 打家劫舍  解法一：动态规划
     *
     * 思想：对于第k(k>2)间房屋，有两个选项：
     * 1.偷窃第k间房屋，那么就不能偷窃第k−1间房屋，偷窃总金额为前k−2间房屋的最高总金额与第k间房屋的金额之和；
     * 2.不偷窃第k间房屋，偷窃总金额为前k−1间房屋的最高总金额。
     *
     * 时间复杂度：O(n)，其中n是数组长度。只需要对数组遍历一次；
     * 空间复杂度：O(n)，使用数组存储整个数组的结果，因此空间复杂度是O(n)；
     *
     * base case：
     *    dp[0] = nums[0]               // 只有一间房屋，则偷窃该房屋
     *    dp[1] = max(nums[0],nums[1])  // 只有两间房屋，选择其中金额较高的房屋进行偷窃
     *
     * DP方程：dp[i] = max(dp[i−2] + nums[i], dp[i−1])
     *
     * https://leetcode-cn.com/problems/house-robber/solution/198-da-jia-jie-she-by-chen-li-guan/
     * @param nums
     * @return
     */
    fun rob(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        if (nums.size == 1) return nums[0]

        val dp = IntArray(nums.size)
        dp[0] = nums[0]
        dp[1] = Math.max(nums[0], nums[1])
        for (i in 2 until nums.size) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1])
        }
        return dp[nums.size - 1]
    }

    /**
     * 198. 打家劫舍  解法二：动态规划（空间优化）
     *
     * 思想：对于第k(k>2)间房屋，有两个选项：
     * 1.偷窃第k间房屋，那么就不能偷窃第k−1间房屋，偷窃总金额为前k−2间房屋的最高总金额与第k间房屋的金额之和；
     * 2.不偷窃第k间房屋，偷窃总金额为前k−1间房屋的最高总金额。
     *
     * 时间复杂度：O(n)，其中n是数组长度。只需要对数组遍历一次；
     * 空间复杂度：O(1)，不使用滚动数组，只存储前两间房屋的最高总金额，而不需要存储整个数组的结果，因此空间复杂度是O(1)；
     *
     * base case：
     *    pre = nums[0]                // 只有一间房屋，则偷窃该房屋
     *    cur = max(nums[0], nums[1])  // 只有两间房屋，选择其中金额较高的房屋进行偷窃
     *
     * DP方程：cur = max(pre + nums[i], cur)
     *
     * https://leetcode-cn.com/problems/house-robber/solution/198-da-jia-jie-she-by-chen-li-guan/
     * @param nums
     * @return
     */
    fun rob1(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        if (nums.size == 1) return nums[0]

        var pre = nums[0]
        var cur = Math.max(nums[0], nums[1])
        for (i in 2 until nums.size) {
            val temp = cur
            cur = Math.max(pre + nums[i], cur)
            pre = temp
        }
        return cur
    }

    /**
     * 198. 打家劫舍  解法二：动态规划（空间优化）- 代码优化
     *
     * base case：
     *    pre = 0
     *    cur = 0
     *
     * DP方程：cur = max(pre + num, cur)
     *
     * @param nums
     * @return
     */
    fun rob2(nums: IntArray): Int {
        var pre = 0
        var cur = 0
        var tmp: Int
        for (num in nums) {
            tmp = cur
            cur = Math.max(pre + num, cur)
            pre = tmp
        }
        return cur
    }

    /**
     * 213. 打家劫舍 II  解法一：动态规划（空间优化）
     *
     * 思想：环状排列意味着第一个房子和最后一个房子中只能选择一个偷窃，因此可以把此环状排列房间问题约化为两个单排排列房间子问题：
     * 1.在不偷窃第一个房子的情况下（即nums[0]），最大金额是p1；
     * 2.在不偷窃最后一个房子的情况下（即nums[n-1]），最大金额是p2；
     * 3.综合偷窃最大金额： 为以上两种情况的较大值，即 max(p1, p2)。
     *
     * 时间复杂度：O(n)，其中n是数组长度。只需要对数组遍历一次；
     * 空间复杂度：O(1)，不使用滚动数组，只存储前两间房屋的最高总金额，而不需要存储整个数组的结果，因此空间复杂度是O(1)；
     *
     * base case：
     *    pre = 0            // pre(0) -- cur(1) -- num(2)
     *    cur = 0
     *
     * DP方程：cur = max(pre + num, cur)
     *
     * https://leetcode-cn.com/problems/house-robber-ii/solution/213-da-jia-jie-she-ii-by-chen-li-guan/
     * @param nums
     * @return
     */
    fun robTwo(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        if (nums.size == 1) return nums[0]

        return Math.max(
                rob1(Arrays.copyOfRange(nums, 1, nums.size)),
                rob1(Arrays.copyOfRange(nums, 0, nums.size - 1))
        )
    }

    /**
     * 221. 最大正方形 解法一：动态规划
     *
     * 思想：以(i,j)为根据点，它最大的正方形的边长取决于(上, 左, 左上)边长最短的那一个
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(mn)，使用了空间大小为mn的数组
     *
     * base case：第 0 行和第 0 列，同步 matrix
     *
     * DP方程：dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1
     *
     * https://leetcode-cn.com/problems/maximal-square/solution/221-zui-da-zheng-fang-xing-by-chen-li-guan/
     * @param matrix
     * @return
     */
    fun maximalSquare(matrix: Array<CharArray>): Int {
        if (matrix.isEmpty() || matrix[0].isEmpty()) return 0

        val m = matrix.size
        val n: Int = matrix[0].size
        var max = 0

        // 初始化base case：定义 dp 数组，相当于已经预处理新增第一行、第一行均为0
        val dp = Array(m + 1) { IntArray(n + 1) }

        // 根据状态转移方程 dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1 进行递推
        for (i in 1..m) {
            for (j in 1..n) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]) + 1
                    max = Math.max(dp[i][j], max)
                } else {
                    dp[i][j] = 0
                }
            }
        }
        return max * max
    }

    /**
     * 221. 最大正方形 解法一：动态规划（空间优化）
     *
     * 思路：增加 northwest 西北角解决"左上角"的问题
     *
     * 时间复杂度：O(mn)，其中m和n分别为行数和列数；
     * 空间复杂度：O(n)，使用了空间大小为n的数组
     *
     * base case：第 0 行和第 0 列，同步 matrix
     *
     * DP方程：dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1
     *
     * https://leetcode-cn.com/problems/maximal-square/solution/221-zui-da-zheng-fang-xing-by-chen-li-guan/
     * @param matrix
     * @return
     */
    fun maximalSquare2(matrix: Array<CharArray>): Int {
        if (matrix.isEmpty() || matrix[0].isEmpty()) return 0

        val m = matrix.size
        val n: Int = matrix[0].size
        var max = 0

        // 初始化base case：定义 dp 数组，相当于已经预处理新增第一行为0
        val dp = IntArray(n + 1)
        // 西北角/左上角
        var northWest = dp[0]

        // 根据状态转移方程 dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1 进行递推
        for (i in 1..m) {
            for (j in 1..n) {
                val temp = dp[j]
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(dp[j - 1], Math.min(dp[j], northWest)) + 1
                    max = Math.max(dp[j], max)
                } else {
                    dp[j] = 0
                }
                northWest = temp
            }
        }
        return max * max
    }
}