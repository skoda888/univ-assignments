#|
    Mathematical model

    level(l1...ln, x, level) = {
        level, if l1 == x

    }

|#

(defun traverse_left (binary_tree number_of_vertices number_of_edges)
    (cond
        ((null binary_tree) nil)    
        ((= number_of_vertices (+ 1 number_of_edges)) nil)
        (t (cons (car binary_tree) (cons (cadr binary_tree) (traverse_left (cddr binary_tree) (+ 1 number_of_vertices) (+ (cadr binary_tree) number_of_edges)))))
    )
)

(defun go_left_branch (binary_tree)
    (traverse_left (cddr binary_tree) 0 0)
)

(defun get_right_subtree (binary_tree number_of_vertices number_of_edges)
    (cond
        ((null binary_tree) nil)    
        ((= number_of_vertices (+ 1 number_of_edges)) binary_tree)
        (t (get_right_subtree (cddr binary_tree) (+ 1 number_of_vertices) (+ (cadr binary_tree) number_of_edges)))
    )
)

(defun wrapper_get_right_subtree (binary_tree)
    (get_right_subtree (cddr binary_tree) 0 0)
)

; (write (go_left_branch '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0)))
; (print (wrapper_get_right_subtree '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0)))


(defun search_level_in_left_subtree (binary_tree number_of_vertices number_of_edges node)
    (cond
        ((null binary_tree) -1)    
        ((= number_of_vertices (+ 1 number_of_edges)) -1)
        ((eq (car binary_tree) node) number_of_vertices)
        (t (search_level_in_left_subtree (cddr binary_tree) (+ 1 number_of_vertices) (+ (cadr binary_tree) number_of_edges node)))
    )
)

(defun get_level (binary_tree node level)
    (cond
        ((null binary_tree) -1)
        ((eq (car binary_tree) node) level)
        ((= -1 (get_level (go_left_branch binary_tree) node (+ 1 level))) (get_level (wrapper_get_right_subtree binary_tree) node (+ 1 level)))
        (t (get_level (go_left_branch binary_tree) node (+ 1 level)))
    )
)


(write (get_level '(a 2 b 0 c 2 d 0 e 0) 'd 0))