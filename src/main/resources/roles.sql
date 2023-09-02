INSERT INTO role (id, role)
SELECT * FROM (VALUES
                   (1, 'Admin'),
                   (2, 'User'),
                   (3, 'Manager'),
                   (4, 'Guest'),
                   (5, 'Moderator')
              ) AS new_roles (id, role)
WHERE NOT EXISTS (
        SELECT 1 FROM role
    );
