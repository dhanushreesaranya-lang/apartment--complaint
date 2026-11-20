import os
import subprocess

JDK_BIN = r"C:\Program Files\Java\jdk-17\bin"
JAVAC = os.path.join(JDK_BIN, "javac.exe") if os.path.exists(os.path.join(JDK_BIN, "javac.exe")) else "javac"
JAVA = os.path.join(JDK_BIN, "java.exe") if os.path.exists(os.path.join(JDK_BIN, "java.exe")) else "java"

def build():
    os.makedirs("bin", exist_ok=True)
    java_files = []
    for root, dirs, files in os.walk("src"):
        for f in files:
            if f.endswith(".java"):
                java_files.append(os.path.join(root, f))
    
    print(f"Compiling {len(java_files)} Java files with {JAVAC}...")
    cmd = [JAVAC, "-cp", "lib/sqlite-jdbc.jar", "-d", "bin"] + java_files
    result = subprocess.run(cmd, capture_output=True, text=True)
    if result.returncode == 0:
        print("Java compilation succeeded!")
    else:
        print("Java compilation failed:")
        print(result.stdout)
        print(result.stderr)
        return False
    return True

def test_run():
    print("Testing Java application execution...")
    cmd = [JAVA, "-cp", "bin;lib/sqlite-jdbc.jar", "com.apartment.Main"]
    result = subprocess.run(cmd, input="1\n1\n6\n", capture_output=True, text=True)
    print("Output preview:")
    lines = result.stdout.splitlines()
    for line in lines[:30]:
        print(line)
    if result.returncode == 0:
        print("\nTest run SUCCESSFUL! Exit code 0.")
    else:
        print("\nTest run FAILED with code", result.returncode)
        print("Error output:", result.stderr)

if __name__ == "__main__":
    if build():
        test_run()
