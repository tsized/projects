SYSCALL_DEFINE0(getpppid) {
    int pid;

    rcu_read_lock();
    pid = task_tgid_vnr(current->real_parent->real_parent);
    rcu_read_unlock();

    return pid;
}

SYSCALL_DEFINE1(getparent, unsigned int, pid) {
    int parent = -1;

    struct task_struct *p;

    for_each_process(p) {
        if (p->pid == pid) {
            rcu_read_lock();
            parent = p->real_parent;
            rcu_read_unlock;
            break;
        }
    }
    return parent;
}

SYSCALL_DEFINE1(zombiecount, unsigned int, pid) {
    int zombies = 0;

    struct task_struct *p;
    struct task_struct *c;

    for_each_process(p) {
        if (p->pid == pid) {
            rcu_read_lock();
            list_for_each_entry(c, &p->children, sibling) {
                if (c->exit_state & EXIT_ZOMBIE) {
                    zombies++;
                }
            }
        }
        rcu_read_unlock();
        break;
    }
    return zombies;
}

