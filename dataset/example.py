#Assignment Test
#Correct Assignment
a = 0
b = None
c = "1"
d = [1, 2, '3', a, [b, c]]
f = d[4]

#Correct operations
b = a + 1
c = a * b
c = a / b
c = a ** (b - 5 % 2)
c -= 3
c /= 5

c = "1" + '2'
a = "a"
b = 'a'
c = a + b
a = [1, 2]
b = ["a", '1', None, a.random()]
c = a + b
c = [1, 2] + ['a', None]

#There is no way to check if function exist for this variable
#so is always accepted and return NA.
c = 1 + object.function()
c = 'a' + object.function()
a = [1, 2, '3', [2]]
c = 1 + a[0]
c = "a" + a[2]
c = [1] + a[3]

#Wrong array access
g = d[5]
g = d[-1]
g = d["a"]
g = d[None]
g = d[f]

# Invalid types for operations
# Setup part - Begin
a = 1
b = "a"
c = None
d = [1]
# Setup part - End

g = a + b
g = a + c
g = a + d
g = a * 'a'
g = a ** b
g = b + c
g = b + d
g = b * 'a'
g = b ** b
g = c + d
g = c * 'a'
g = c ** b
g = d * 'a'
g = d ** b
g = a + open(1, 1)
g = a + type(a)

# If statement tests
a = 0
b = -1

#Should produce error
if a > b:
    g = 1 + "a"

#Should NOT produce error
if a <= b:
    g = 1 + "a"

#Should produce error
if a <= b or not false:
    g = 1 + "a"

#Should produce error
if a != b:
    g = 1 + "a"

#Should produce error
if true and true or true and false:
    g = 1 + "a"

#Should produce error
if [1, 2, 'a'] < [1, 3]:
    g = 1 + "a"

#Should produce error
if [1, 2] < [1, 2, 3]:
    g = 1 + "a"

#Should produce error at condition line
if ['a', 2] < [1, 2, 3]:
    g = 1 + "a"

#Should produce error
if ['a', 2] != [1, 2, 3]:
    g = 1 + "a"

#Should NOT produce error
if a > object.function():
    g = 1 + "a"

#Should produce error at condition line
if a > [1]:
    g = 1 + "a"

#Should produce error
if a != [1]:
    g = 1 + "a"

#Should NOT produce error
if a == [1]:
    g = 1 + "a"

#For statement tests
# Setup part - Begin
a = 0
b = 'a'
c = None
d = [1, 2, 3, 'a']
f = object.function()
# Setup part - End

#Should produce error at for line
for i in a:
    g = 1 + i

#Should produce error at iteration 1
for i in b:
    g = 1 + i

#Should produce error at for line
for i in c:
    g = 1 + i

#Should produce error at iteration 4
for i in d:
    g = 1 + i

#Should produce error error at iteration 1
#(iteration number not shown because it only runs one time)
for i in f:
    g = 1 + i + 'a'

#While statement tests
# Setup part - Begin
a = 0
b = 3
# Setup part - End

#should produce 10 error until max iteration depth reached
while a < b:
    g = None + "a"

# Should produce error at addition inside if
i = 3
while i > a:
    i -= 1

if i == 0:
    i = 1 + "a"

#Function definition tests

def function_1(x, y = 0):
    return x + y

#Should produce error during definition parsing since multiplication with the default value type is invalid
def function_2(x, y = 'a'):
    return x * y

def function_3(x, y = 1):
    return function_1(x, x) + function_2(x, y) * function_1(y)

#Duplicate function is discarded at second pass
def function_1(x):
    return x + x

#Duplicate function is discarded at second pass.
#No check are performed since the function itself should not exist
def function_1(x, y = 0, z = 0):
    return 1 + a

#Function tests

#Should produce error at addition inside if
a = function_1(1)

if a == 1:
    g = 1 + 'a'

#Should produce error inside function_1. Return value is NA
a = function_1(1, 'a')

#Should produce error at addition inside if
a = function_2(1, 3)

if a == 3:
    g = 1 + 'a'

#Should produce error at addition inside if
a = function_3(1, 5)

if a == 27:
    g = 1 + 'a'

#Should produce error inside function_1 and function_2. Return value is NA
a = function_3('a', 'b')

#Max - Min tests

#Should produce error at addition inside if
a = max(1, 8, 5, -1, 2)

if a == 8:
    g = 1 + 'a'

#Should produce error at addition inside if
a = min(1, 8, 5, -1, 2)

if a == -1:
    g = 1 + 'a'

#Should produce error for every invalid type
a = max(1, 2, 'a', None, b.arg())

#Should produce error for every invalid type
a = max("b", 2, 'a', None, b.arg())

#Should produce error at addition inside if
a = min('a', 'banana', '32')

if a == "32":
    g = 1 + 'a'

#Should produce error at addition inside if
a = max('a', 'banana', '32')

if a == "banana":
    g = 1 + 'a'