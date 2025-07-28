INSERT IGNORE INTO posts (id, title, content, post_type, external_link, keanu_moment, author, profile_picture, created_at, is_deleted) VALUES
                                                                                                                                    (1, 'How much memory does Java consume?', 'Discussion about Java memory consumption.', 'TEXT', NULL, NULL, 'Alice', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (2, 'Understanding Spring Boot internals', 'Deep dive into Spring Boot architecture.', 'MARKDOWN', NULL, NULL, 'Bob', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (3, 'Best practices for REST APIs', 'Guidelines for designing scalable REST APIs.', 'TEXT', NULL, NULL, 'Carol', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (4, 'Introduction to Kubernetes', 'Basics of Kubernetes orchestration.', 'MARKDOWN', NULL, NULL, 'Dave', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (5, 'Effective unit testing strategies', 'How to write better unit tests.', 'TEXT', NULL, NULL, 'Eve', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (6, 'Microservices vs Monolith', 'Pros and cons of microservices architecture.', 'MARKDOWN', NULL, NULL, 'Frank', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (7, 'Getting started with Docker', 'A beginner’s guide to Docker containers.', 'TEXT', NULL, NULL, 'Grace', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (8, 'Async programming in Java', 'Techniques and tools for asynchronous Java.', 'MARKDOWN', NULL, NULL, 'Heidi', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (9, 'Guide to Hibernate caching', 'How Hibernate cache works and best practices.', 'TEXT', NULL, NULL, 'Ivan', '/default-anonymous.png', NOW(), FALSE),
                                                                                                                                    (10, 'Understanding Java Streams API', 'Using streams effectively in Java 8+', 'MARKDOWN', NULL, NULL, 'Judy', '/default-anonymous.png', NOW(), FALSE);

-- Post 2 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (1, 'Great explanation about Spring Boot starters!', 'Tom', '/default-anonymous.png', NOW(), 2, NULL, FALSE),
                                                                                                                 (2, 'Can you add more on auto-configuration?', 'Jerry', '/default-anonymous.png', NOW(), 2, NULL, FALSE),
                                                                                                                 (3, 'What about actuator endpoints?', 'Nina', '/default-anonymous.png', NOW(), 2, NULL, FALSE);

-- Post 3 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
    (4, 'I love RESTful design principles.', 'Oscar', '/default-anonymous.png', NOW(), 3, NULL, FALSE);

-- Post 4 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (5, 'Kubernetes simplifies deployment a lot.', 'Pam', '/default-anonymous.png', NOW(), 4, NULL, FALSE),
                                                                                                                 (6, 'What about Helm charts?', 'Quinn', '/default-anonymous.png', NOW(), 4, NULL, FALSE);

-- Post 5 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
    (7, 'Unit testing can be tricky but rewarding.', 'Rick', '/default-anonymous.png', NOW(), 5, NULL, FALSE);

-- Post 6 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (8, 'Microservices offer great scalability.', 'Sara', '/default-anonymous.png', NOW(), 6, NULL, FALSE),
                                                                                                                 (9, 'Monoliths are easier to manage initially.', 'Tim', '/default-anonymous.png', NOW(), 6, NULL, FALSE),
                                                                                                                 (10, 'Can we combine both approaches?', 'Uma', '/default-anonymous.png', NOW(), 6, NULL, FALSE);

-- Post 7 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
    (11, 'Docker makes deployment simple!', 'Vince', '/default-anonymous.png', NOW(), 7, NULL, FALSE);

-- Post 8 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (12, 'Async programming helps improve performance.', 'Wendy', '/default-anonymous.png', NOW(), 8, NULL, FALSE),
                                                                                                                 (13, 'CompletableFuture is powerful.', 'Xander', '/default-anonymous.png', NOW(), 8, NULL, FALSE);

-- Post 9 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
    (14, 'Hibernate caching saves a lot of DB calls.', 'Yara', '/default-anonymous.png', NOW(), 9, NULL, FALSE);

-- Post 10 comments
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (15, 'Streams API makes collections manipulation easier.', 'Zane', '/default-anonymous.png', NOW(), 10, NULL, FALSE),
                                                                                                                 (16, 'What about parallel streams?', 'Amy', '/default-anonymous.png', NOW(), 10, NULL, FALSE),
                                                                                                                 (17, 'Collectors are very useful!', 'Brian', '/default-anonymous.png', NOW(), 10, NULL, FALSE),
                                                                                                                 (18, 'Can you share more examples?', 'Clara', '/default-anonymous.png', NOW(), 10, NULL, FALSE),
                                                                                                                 (19, 'Lambda expressions are key.', 'Derek', '/default-anonymous.png', NOW(), 10, NULL, FALSE);

-- Level 1 (top-level, parent_comment_id=NULL)
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (20, 'How does Java manage heap memory?', 'Alice', '/default-anonymous.png', NOW(), 1, NULL, FALSE),
                                                                                                                 (21, 'What is JVM stack size?', 'Bob', '/default-anonymous.png', NOW(), 1, NULL, FALSE),
                                                                                                                 (22, 'Garbage Collection tuning tips?', 'Carol', '/default-anonymous.png', NOW(), 1, NULL, FALSE),
                                                                                                                 (23, 'Can Java memory leaks occur?', 'Dave', '/default-anonymous.png', NOW(), 1, NULL, FALSE),
                                                                                                                 (24, 'Impact of large objects on memory?', 'Eve', '/default-anonymous.png', NOW(), 1, NULL, FALSE),
                                                                                                                 (25, 'Best tools for memory profiling?', 'Frank', '/default-anonymous.png', NOW(), 1, NULL, FALSE);

-- Level 2 (each child of level 1 comment)
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (26, 'Heap is divided into generations.', 'Grace', '/default-anonymous.png', NOW(), 1, 20, FALSE),
                                                                                                                 (27, 'Stack size depends on thread configuration.', 'Heidi', '/default-anonymous.png', NOW(), 1, 21, FALSE),
                                                                                                                 (28, 'Use G1GC for most workloads.', 'Ivan', '/default-anonymous.png', NOW(), 1, 22, FALSE),
                                                                                                                 (29, 'Yes, memory leaks are possible in Java.', 'Judy', '/default-anonymous.png', NOW(), 1, 23, FALSE),
                                                                                                                 (30, 'Large objects cause more GC overhead.', 'Karl', '/default-anonymous.png', NOW(), 1, 24, FALSE),
                                                                                                                 (31, 'VisualVM and YourKit are popular tools.', 'Liam', '/default-anonymous.png', NOW(), 1, 25, FALSE);

-- Level 3 (each child of level 2 comment)
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (32, 'Young generation collects frequently.', 'Mia', '/default-anonymous.png', NOW(), 1, 26, FALSE),
                                                                                                                 (33, 'Thread stack size default is 1MB.', 'Noah', '/default-anonymous.png', NOW(), 1, 27, FALSE),
                                                                                                                 (34, 'CMS collector is deprecated.', 'Olivia', '/default-anonymous.png', NOW(), 1, 28, FALSE),
                                                                                                                 (35, 'Leaks usually come from static references.', 'Paul', '/default-anonymous.png', NOW(), 1, 29, FALSE),
                                                                                                                 (36, 'Object pooling can help.', 'Quinn', '/default-anonymous.png', NOW(), 1, 30, FALSE),
                                                                                                                 (37, 'JProfiler offers advanced profiling.', 'Rose', '/default-anonymous.png', NOW(), 1, 31, FALSE);

-- Level 4
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (38, 'Old generation is collected less frequently.', 'Sam', '/default-anonymous.png', NOW(), 1, 32, FALSE),
                                                                                                                 (39, 'Stack overflow errors relate to size.', 'Tina', '/default-anonymous.png', NOW(), 1, 33, FALSE),
                                                                                                                 (40, 'ZGC is a low-latency GC introduced recently.', 'Uma', '/default-anonymous.png', NOW(), 1, 34, FALSE),
                                                                                                                 (41, 'Use weak references to avoid leaks.', 'Victor', '/default-anonymous.png', NOW(), 1, 35, FALSE),
                                                                                                                 (42, 'Pooling can increase throughput.', 'Wendy', '/default-anonymous.png', NOW(), 1, 36, FALSE),
                                                                                                                 (43, 'Profiling can identify bottlenecks.', 'Xander', '/default-anonymous.png', NOW(), 1, 37, FALSE);

-- Level 5
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (44, 'GC pauses depend on heap size.', 'Yara', '/default-anonymous.png', NOW(), 1, 38, FALSE),
                                                                                                                 (45, 'Increase stack size carefully.', 'Zane', '/default-anonymous.png', NOW(), 1, 39, FALSE),
                                                                                                                 (46, 'ZGC minimizes pause times.', 'Amy', '/default-anonymous.png', NOW(), 1, 40, FALSE),
                                                                                                                 (47, 'WeakHashMap helps manage references.', 'Brian', '/default-anonymous.png', NOW(), 1, 41, FALSE),
                                                                                                                 (48, 'Pooling can increase complexity.', 'Clara', '/default-anonymous.png', NOW(), 1, 42, FALSE),
                                                                                                                 (49, 'Profiling adds overhead but is worth it.', 'Derek', '/default-anonymous.png', NOW(), 1, 43, FALSE);

-- Level 6 (final nested level)
INSERT IGNORE INTO comments (id, content, author, profile_picture, created_at, post_id, parent_comment_id, deleted) VALUES
                                                                                                                 (50, 'Monitoring tools help track GC.', 'Eva', '/default-anonymous.png', NOW(), 1, 44, FALSE),
                                                                                                                 (51, 'Beware of too large stack size.', 'Fred', '/default-anonymous.png', NOW(), 1, 45, FALSE),
                                                                                                                 (52, 'ZGC support varies by JVM version.', 'Gina', '/default-anonymous.png', NOW(), 1, 46, FALSE),
                                                                                                                 (53, 'Avoid strong references for caches.', 'Hank', '/default-anonymous.png', NOW(), 1, 47, FALSE),
                                                                                                                 (54, 'Pooling requires thread-safety.', 'Ivy', '/default-anonymous.png', NOW(), 1, 48, FALSE),
                                                                                                                 (55, 'Profiling snapshots can be saved.', 'Jack', '/default-anonymous.png', NOW(), 1, 49, FALSE);
