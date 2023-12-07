def read_file_into_lines(file_path):
    with open(file_path, 'r') as file:
        return file.readlines()
