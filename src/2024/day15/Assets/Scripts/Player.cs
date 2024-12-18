using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public class Player : MonoBehaviour
{
    public float moveSpeed = 20f;
    public bool isMoving;
    public Vector2 lastPlayerMove;
    public Solver solver;

    private Vector2 _moveDirection;
    private List<Box> _boxes;
    
    public IEnumerator MovePlayer(Vector2 direction)
    {
        if (!CanPlayerMove(direction)) yield break;
        
        var targetPosition = (Vector2)transform.position + direction;
        lastPlayerMove = direction;
        
        yield return MoveToPosition(targetPosition);
    }

    public void FixedUpdate()
    {
        if (isMoving) return;
        
        _moveDirection = Vector2.zero;
        if (Input.GetKeyDown(KeyCode.W)) _moveDirection = Vector2.up;
        if (Input.GetKeyDown(KeyCode.S)) _moveDirection = Vector2.down;
        if (Input.GetKeyDown(KeyCode.A)) _moveDirection = Vector2.left;
        if (Input.GetKeyDown(KeyCode.D)) _moveDirection = Vector2.right;

        if (_moveDirection == Vector2.zero) return;
        
        var targetPosition = (Vector2)transform.position + _moveDirection;
        StartCoroutine(MoveToPosition(targetPosition));
    }

    private bool GetCanPlayerMoveSingleWidth(Vector2 direction, int boxWidth)
    {
        var hitObjects = Physics2D.RaycastAll(transform.position, direction);

        var wall = hitObjects.First(obj => obj.collider.gameObject.CompareTag("Wall"));
        var wallDistance = Mathf.RoundToInt(wall.distance) - 1;

        var boxesBeforeWall = hitObjects
            .Where(obj => obj.collider.gameObject.CompareTag("Box") && obj.distance < wallDistance);

        return wallDistance > boxesBeforeWall.Count() * boxWidth;
    }

    private bool GetCanPlayerMoveDoubleWidth(Vector2 direction) => (direction.x, direction.y) switch
    {
        (-1f, 0) or (1f, 0) => GetCanPlayerMoveHorizontal(direction),
        (0, 1f) or (0, -1f) => GetCanPlayerMoveVertical(direction),
        _ => throw new ArgumentOutOfRangeException(nameof(direction), direction, null)
    };

    private bool GetCanPlayerMoveHorizontal(Vector2 direction)
    {
        throw new NotImplementedException();
    }

    private bool GetCanPlayerMoveVertical(Vector2 direction) => GetCanPlayerMoveSingleWidth(direction, boxWidth: 2);
    
    private bool CanPlayerMove(Vector2 direction) => solver.doubleSizeMode
        ? GetCanPlayerMoveDoubleWidth(direction)
        : GetCanPlayerMoveSingleWidth(direction, boxWidth: 1);
    
    public void SetBoxes(List<Box> boxes) => _boxes = boxes;

    private IEnumerator MoveToPosition(Vector2 targetPosition)
    {
        isMoving = true;
        
        while ((Vector2)transform.position != targetPosition)
        {
            transform.position = Vector2.MoveTowards(transform.position, targetPosition, moveSpeed * Time.deltaTime);
            yield return null;
        }

        isMoving = false;
    }
}
