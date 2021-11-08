package ua.com.alevel.level_2;

public class BinaryTree {

    public BinaryTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(4);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(6);
        root.right.right = new TreeNode(8);
        root.right.right.left = new TreeNode(7);

        System.out.println(maxDepth(root));
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private int maxDepth(TreeNode root) {
        if (root == null) {
            return -1;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }
}
