学习笔记

四、二分查找代码模板
public int binarySearch(int[] array, int target) {
    int left = 0, right = array.length - 1, mid;
    while (left <= right) {
        mid = (right - left) / 2 + left;

        if (target == array[mid]) {
            return mid;
        } else if (target < array[mid]) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }

    return -1;
}

五、BFS模板-迭代
fun levelOrder(root: TreeNode?): List<List<Int>>? {
    val res: MutableList<List<Int>> = mutableListOf()  // 存放最终结果的集合
    if (root == null) return res

    val queue: Queue<TreeNode> = LinkedList()   // 创建一个队列，将根节点放入其中
    queue.offer(root)
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


六、DFS模板
1、递归
fun levelOrder1(root: TreeNode?): List<List<Int>>? {
    val res: MutableList<MutableList<Int>> = mutableListOf()  // 存放最终结果的集合
    if (root == null) return res

    dfsLevel(1, root, res)
    return res
}

fun dfsLevel(index: Int, root: TreeNode?, res: MutableList<MutableList<Int>>) {
    if (root == null) return

    if (res.size < index) {      // 假设res是[ [1],[2,3] ]， index是3，就再插入一个空list放到res中
        res.add(mutableListOf())
    }

    res[index - 1].add(root.`val`)   // 将当前节点的值加入到res中，index代表当前层，假设index是3，节点值是6。res是[ [1],[2,5],[3,4] ]，加入后res就变为 [ [1],[2,5],[3,4,6] ]

    if (root.left != null) {    // 递归的处理左子树，右子树，同时将层数index+1
        dfsLevel(index + 1, root.left, res)
    }
    if (root.right != null) {
        dfsLevel(index + 1, root.right, res)
    }
}

2、迭代
fun preorder(root: Node?): List<Int> {
    val res: MutableList<Int> = mutableListOf()
    val stack: Deque<Node?> = LinkedList()

    if (root == null) return res else stack.push(root)

    while (!stack.isEmpty()) {
        val p = stack.pop()    // 将根节点弹出
        res.add(p!!.`val`)     // 加入到结果集合中

        for (i in p.children.size - 1 downTo 0) {   // 将该节点的子节点从右往左压入栈
            stack.push(p.children[i])
        }
    }
    return res
}

fun postorder(root: Node?): List<Int> {
    val res: MutableList<Int> = mutableListOf()
    val stack: Deque<Node?> = LinkedList()

    if (root == null) return res else stack.push(root)

    while (!stack.isEmpty()) {
        val p = stack.pop()     // 将根节点弹出
        res.add(p!!.`val`)      // 加入到结果集合中

        for (i in 0 until p.children.size) {  // 将该节点的子节点从左往右压入栈
            stack.push(p.children[i])
        }
    }
    return res.reversed()  // 最后将list反转
}