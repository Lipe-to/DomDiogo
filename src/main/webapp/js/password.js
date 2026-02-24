const input = document.getElementsByName('password')[0];
const button = document.getElementsByName('toggle')[0];
const open = document.getElementById('open');
const closed = document.getElementById('closed');

    button.addEventListener('click', () => {
      if (input.type === 'password') {
        input.type = 'text';
        open.style.display = 'none';
        closed.style.display = 'inline';
    } else {
        input.type = 'password';
        open.style.display = 'inline';
        closed.style.display = 'none';
      }
});