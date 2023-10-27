select * from groups;
select * from groups_users;
select * from payment_blocks ;
select * from transactions;
select * from users ;
select * from users_groups;



drop table groups;
drop table groups_users;
drop table  payment_blocks ;
drop table  transactions;
drop table  users ;
drop table users_groups;



user1 registers
user2 registers

user1 creates a group
-- user1 group list get updated
-- server downloads all users and adds people to a temp list
-- user1 can add user2 from temp list to group
-- user2 list gets updated

user2 refreshes and finds group


Initial features :
only a user can save his/her expenses. User cannot save other user's expenses
user can create a group
user can add participants to the group

Example : Group G1 has users U1, U2, U3

U1 spends 30 for : U1, U2, U3   : /pay {param : payeeUserId, paid for users : list(userIds), amount, date}
U2 spends 50 for : U1, U2
U2 spends 30 for : U3


corresponding payment blocks :
payee    receiver    amount
u1          u1          10
u1          u2          10
u1          u3          10
u2          u1          25
u2          u2          25
u2          u3          30



Controllers :
user controller (/user):
/new
/delete
/all



group controller ->
/new/group ->


paymentBlock controller ->













